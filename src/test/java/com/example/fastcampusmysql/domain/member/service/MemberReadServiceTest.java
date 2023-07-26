package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.utill.MemberFixtureFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberReadServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberReadService memberReadService;

    private List<Member> mockMemberList;

    @BeforeEach
    void setUp() {
        mockMemberList = new ArrayList<>();
        for(int i=0; i<10; i++){
            mockMemberList.add(MemberFixtureFactory.create());
        }
    }

    @DisplayName("회원 id로 회원 정보를 조회하여 dto객체로 리턴한다")
    @Test
    void testGetMember() {
        // given
        Member mockMember = mockMemberList.get(0);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(mockMember));
        // when
        MemberDto resultMember = memberReadService.getMember(mockMember.getId());
        // then
        assertThat(resultMember.nickname(), is(mockMember.getNickname()));
    }

    @DisplayName("N개의 회원 id로 한번에 회원정보 조회. 리스트를 리턴")
    @Test
    void testGetMembers() {
        // given
        List<Long> ids = mockMemberList.stream()
                .map(Member::getId)
                .toList();

        given(memberRepository.findAllByIdIn(any())).willReturn(mockMemberList);

        // when
        List<MemberDto> members = memberReadService.getMembers(ids);

        // then
        Assertions.assertThat(members).extracting("id").containsAll(ids);
    }

}
