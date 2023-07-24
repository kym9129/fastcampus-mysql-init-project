package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Disabled
    @Test
    public void getMapping_requestBody_test() throws Exception {
        Long memberId = 2L;
        LocalDate firstDate = LocalDate.of(2022, 11, 11);
        LocalDate lastDate = LocalDate.of(2022, 11, 13);
        String content = objectMapper.writeValueAsString(new DailyPostCountRequest(memberId, firstDate, lastDate));

        mockMvc.perform(get("/posts/daily-post-counts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
        ).andDo(print());

    }

    @DisplayName("포스트 등록 테스트")
    @Test
    void post_test() throws Exception {
        // given
        Long memberId = 1L;
        String contents = "testPost";
//        String content = objectMapper.writeValueAsString(new PostCommand(memberId, contents));
//        System.out.println("content = " + content);

        // when /posts
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new PostCommand(memberId, contents))) // todo: null 원인 확인
        ).andDo(print());

        // then
        mockMvc.perform(get("/posts/members/"+memberId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"page\":0, \"size\":1, \"sort\": [\"createdDate,DESC\"]}")
        ).andDo(print());

    }
}
