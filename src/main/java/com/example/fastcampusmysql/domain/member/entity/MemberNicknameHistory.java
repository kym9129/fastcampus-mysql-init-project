package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity(name = "member_nickname_history")
public class MemberNicknameHistory extends BaseTimeEntity {
    // 히스토리성 데이터는 정규화 대상이 아니다.
    // 정규화 시 항상 최신 상태를 유지해야 하는 데이터인지를 고려해야한다.

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "member_id")
    private final Long memberId;

    @Column(name = "nickname", nullable = false, length = 20)
    private final String nickname;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
    }
}
