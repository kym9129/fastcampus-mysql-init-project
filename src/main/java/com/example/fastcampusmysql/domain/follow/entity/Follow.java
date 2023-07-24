package com.example.fastcampusmysql.domain.follow.entity;

import com.example.fastcampusmysql.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_member_id")
    private Long fromMemberId;

    @Column(name = "to_member_id")
    private Long toMemberId;

    /**
     * [고민포인트]
     * 팔로워를 표시할 때 이름이 항상 따라올텐데 id만 갖고있다가 이름은 별도 테이블에서 가져오는게 나을 것인가
     * 아니면 팔로워의 이름도 갖고있는게 나을 것인가
     * 데이터를 최신 상태로 유지할 필요가 있는가? -> 있다
     * (3달 전에 닉네임 바꿨는데 계속 예전 닉네임으로 보여주면 안되니까. 닉네임 변경은 자주 일어나는 일이다.)
     * -> 데이터 정규화 고고
     *
     * [고민포인트2]
     * 식별자를 가지고 닉네임을 어떻게 가져올 것인가? join, 별도 쿼리로 불러오기, ...
     * -> 이번 프로젝트에서는 쿼리를 한번 더 하는 걸로
     *
     * [join은 가능한 미루는 것이 좋은 이유]
     * 1.follow service에서 member가 들어오면 강결합이 일어나기 때문에
     * 프로젝트 초기부터 이런 강결합이 이루어지면 추후 유연성있는 아키텍처나 시스템이 되기 힘들다
     * 2. 결합이 강하기 때문에 추후 아키텍처 적으로 성능 문제를 풀기가 어려워짐.
     * 3. 리펙토링도 어려워짐
     */

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
    }
}
