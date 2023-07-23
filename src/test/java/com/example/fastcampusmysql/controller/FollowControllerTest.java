package com.example.fastcampusmysql.controller;

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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FollowControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("fromMember가 toMember를 팔로우 한다.")
    @Test
    void createFollowMemberTest() throws Exception {
        // 2명 회원가입
        Member fromMember = MemberFixtureFactory.create();
        Member toMember = MemberFixtureFactory.create();

        memberRepository.save(fromMember);
        memberRepository.save(toMember);

        // 가입한 회원끼리 follow
        mockMvc.perform(post("/follow/"+fromMember.getId()+"/"+toMember.getId()))
                .andExpect(status().isOk());

        // fromMember의 follow 목록 중에 toMember가 포함되어있는지 검증
        mockMvc.perform(get("/follow/members/"+fromMember.getId()))
                .andExpect(status().isOk())
//                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[*].id", hasItem(toMember.getId().intValue())));
    }
}
