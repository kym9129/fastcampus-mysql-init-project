package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.utill.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName(){
        // MemberFixtureFactory 사용 예
//        LongStream.range(0, 10)
//                .mapToObj(i -> MemberFixtureFactory.create(i))
//                .forEach(member -> {
//                    System.out.println(member.getNickname());
//                });

        Member member = MemberFixtureFactory.create();
        String expectedName = "boo";

        member.changeNickname(expectedName);

        Assertions.assertEquals(expectedName, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength(){
        Member member = MemberFixtureFactory.create();
        String overMaxLengthName = "booooooooooo";

        Assertions.assertThrows(
                IllegalArgumentException.class, // 커스텀 Exception 사용 시 그걸 적용
                () -> member.changeNickname(overMaxLengthName)
        );
    }
}
