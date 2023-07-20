package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.utill.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FollowControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FollowRepository followRepository;

    @DisplayName("fromMember가 toMember를 팔로우 한다.")
    @Test
    void createFollowMemberTest() throws Exception {
        // 2명 회원가입
        Member fromMember = MemberFixtureFactory.create();
        Member toMember = MemberFixtureFactory.create();

        memberRepository.save(fromMember);
        memberRepository.save(toMember);

        // 가입한 회원끼리 follow
        String url = String.format("/follow/%s/%s", fromMember.getId(), toMember.getId());
        mockMvc.perform(post(url))
                .andExpect(status().isOk());

        // fromMember의 follow 목록 중에 toMember가 포함되어있는지 검증
        List<Follow> folloingList = followRepository.findAllByFromMemberId(fromMember.getId());
        assertTrue(folloingList.stream()
                .anyMatch(follow -> follow.getToMemberId().equals(toMember.getId())));
    }
}
