package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "email", nullable = false, length = 20)
    private final String email;

    @Column(name = "birthday", nullable = false)
    private final LocalDate birthday;

    private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changeNickname(String to){
        Objects.requireNonNull(to);
        validateNickname(to);
        nickname = to;
    }

    private void validateNickname(String nickname){
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
