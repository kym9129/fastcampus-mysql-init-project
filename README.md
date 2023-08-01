# SNS 프로젝트

### 🥅 개요 / 목적

- 대용량 시스템을 다루기 위한 데이터베이스 기본 지식 학습용 **SNS 구현 프로젝트**
    - 정규화, 인덱스, 페이지네이션 최적화, 트랜잭션, 동시성 제어 학습 (🔗 [학습노트](https://www.notion.so/f6b0961169644b00abcc984b875a1b95?pvs=21))
- JDBC Template에서 Spring Data JPA로 리펙토링
- redis의 다양한 자료구조를 활용한 비즈니스 로직 구현
- 서비스 레이어가 아닌 도메인에서 비즈니스 로직을 책임지는 아키텍처 연습
- 배포 자동화와 Docker 연습

### 🍎 주요 기능

- 회원 시스템
    - 회원정보 등록, 조회, 닉네임 수정
    - 닉네임 변경 이력 조회
- 팔로우 시스템
    - 팔로우 등록, 팔로우 중인 목록 조회
- 포스트 시스템
    - 포스트 등록
    - 작성자 별 포스트 목록 조회 : 포스트 별 좋아요 수 표시
    - 작성자 별 일일 포스트 수 조회
    - 타임라인 : 팔로우 중인 회원들의 신규 포스트 목록 조회
    - 좋아요 기능 : 포스트에 좋아요 등록

### 🏗️ 프로젝트 구조
<img width="940" alt="image" src="https://github.com/kym9129/fastcampus-mysql-init-project/assets/72649415/0a2f05d5-e70a-4dfa-b29f-8636386b0477">


### ⚒️ 사용 기술

JAVA 17, SpringBoot 2.7.2, MySQL 8.0, JPA, QueryDSL, Redis 6, EasyRandom, Github Action, Docker

### ✅ 포인트

- **하나의 포스트에 여러명이 동시에 ‘좋아요’를 누르는 동시성 이슈 고려**
    - v2 : ‘좋아요’ 수 집계 저장위치 분리
        - v.2.2 : Redis의 Set 자료구조를 이용해 ‘좋아요’ 기능 구현
            - 좋아요 추가 : `SADD post:like:{memberId} {postId}` (🔗[code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/domain/post/service/PostLikeWriteService.java#L24))
            - 좋아요 수 조회 : `SCARD post:like:{memberId}` (🔗[code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/domain/post/service/PostReadService.java#L41))
            - 포스트 목록 조회 응답시간 단축 (비교시간 추가)
        - v2.1
            - ‘좋아요’를 누를 때마다 별도 저장소에 insert하여 Race Condition 발생하지 않도록 함.
            - 포스트 목록 조회 시 postId 수 만큼 ‘좋아요’ 수의 count() 조회가 필요 → 읽기 부하 증가
    - v1 : Optimistic Lock으로 구현 (🔗 [code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/domain/post/entity/Post.java#L30C32-L30C32))
        - Optimistic Lock을 이용해 transaction version을 비교하며 Post.likeCount 값 업데이트
- **Fan Out On Write (Push Model) 적용하여 타임라인 구현 (🔗 [code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/application/usecase/CreatePostUsecase.java#L29-L38))**
    - 포스트를 등록하는 시점에 작성자를 팔로잉하는 회원의 Timeline에도 insert하여 읽기 최적화
- **Easy-Random을 사용하여 테스트용 예제 객체를 만들어주는 ObjectMother 구현 (🔗[code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/master/src/test/java/com/example/fastcampusmysql/utill/MemberFixtureFactory.java))**
    - 테스트에 필요한 랜덤 데이터를 빠르게 생성하여 테스트코드 작성 시간 감소

### 🔥 트러블 슈팅
- No matching manifest for linux/armv7 in the manifest list entries
    - 문제상황 : 배포 서버에서 docker compose up 하는 도중 발생
    - 원인 : 배포서버에 설치한 Rasbian OS는 ARM 32비트였으나 컨테이너를 띄우려는 도커 이미지들은 64비트를 지원
    - 해결 : 64비트 Ubuntu 설치

- No Default constructor for entity
    - 문제상황 : 스프링 컨테이너 실행 시 발생
    - 원인 : hibernate에서 기본생성자를 필요로 하는데 Entity객체에 기본생성자가 없어서 발생한 에러
    - 해결 : `@NoArgsConstructor`를 사용하면서 entity객체의 불변성을 보장하기 위해 `AccessLevel을 PROECTED`로 설정 (🔗 [code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/domain/member/entity/Member.java#L17))
- NoSuchMethodException
    - 문제상황 : QueryDSL의 select문을 fetch하는 도중에 발생
    - 원인 : Projection용 DTO객체를 record 클래스로 사용하였음. record는 필드가 없이 생성자를 통해서만 데이터를 받는 구조이지만 `Projections.fields()`는 필드 주입 방식이기 때문에 에러가 발생했음.
    - 해결 : `Projections.constructor()`로 변경 (🔗 [code](https://github.com/kym9129/fastcampus-mysql-init-project/blob/4c370aa3436c2f68fa5aef0364b89dbebec539af/src/main/java/com/example/fastcampusmysql/domain/post/repository/PostRepositoryCustomImpl.java#L23))
