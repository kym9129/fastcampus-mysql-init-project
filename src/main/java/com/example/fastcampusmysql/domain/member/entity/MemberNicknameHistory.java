package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity(name = "member_nickname_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNicknameHistory extends BaseTimeEntity {
    // 히스토리성 데이터는 정규화 대상이 아니다.
    // 정규화 시 항상 최신 상태를 유지해야 하는 데이터인지를 고려해야한다.

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Builder
    public MemberNicknameHistory(Long id, Member member, String nickname) {
        this.id = id;
        this.member = Objects.requireNonNull(member);
        this.nickname = Objects.requireNonNull(nickname);
    }
}
