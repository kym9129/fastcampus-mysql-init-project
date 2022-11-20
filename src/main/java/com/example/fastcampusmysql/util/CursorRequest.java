package com.example.fastcampusmysql.util;

// 커서 키 설정 중요. 이번 실습에선 PK를 key로 사용
// 커서 키 조건
//  - 인덱스가 있어야 함
//  - 중복값이 없어야 함
public record CursorRequest(Long key, int size) {

    public static final long NONE_KEY = -1L; // 마지막 데이터(마지막 키)임을 의미. auto_increment여서 나올 수 없는 -1로 설정

    /**
     * key가 있는지 체크
     */
    public boolean hasKey() {
        return key != null;
    }

    /**
     * 1번 키를 받아서 데이터 내려주고 그 다음에 클라이언트가 사용할 next 메소드
     * @param key
     * @return
     */
    public CursorRequest next(Long key){
        return new CursorRequest(key, size);
    }
}
