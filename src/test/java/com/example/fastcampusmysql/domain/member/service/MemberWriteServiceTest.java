package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.utill.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberWriteServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    @InjectMocks
    private MemberWriteService memberWriteService;

    @DisplayName("회원정보 등록")
    @Test
    void testRegister() {
        // given
        String email = "email@email.com";
        String nickname = "nicknick";
        LocalDate birthday = LocalDate.of(2020, 5, 31);
        RegisterMemberCommand command = new RegisterMemberCommand(email, nickname, birthday);

        Member givenMember = MemberFixtureFactory.create();
        given(memberRepository.save(any())).willReturn(givenMember);

        // when
        MemberDto registeredMember = memberWriteService.register(command);

        // then
        // 그 아이디로 멤버 조회했을 때 같은 id가 나오는지
        assertThat(registeredMember.nickname(), is(givenMember.getNickname()));
        // save 메소드가 1번 호출됐는지
        verify(memberRepository, times(1)).save(any());

    }

    @DisplayName("회원 닉네임 변경")
    @Test
    void testChangeNickname() {
        // given

        // when
//        memberWriteService.changeNickname();

        // then
        // 닉넴 바꼈는지 memberId로 db 조회해봐야할거같은데, 통합테스트를 해야하지 않을까?
    }
}
