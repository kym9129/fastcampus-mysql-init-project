package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand(
        String email,
        String nickname,
        LocalDate birthday
) {
    // 레코드 : 자바16부터 공식 기능 됐음
    // getter setter 자동으로 만들어주고 필드를 property로 사용할 수 있음
}
