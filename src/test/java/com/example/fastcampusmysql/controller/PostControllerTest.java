package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getMapping_requestBody_test() throws Exception {
        Long memberId = 2L;
        LocalDate firstDate = LocalDate.of(2022, 11, 11);
        LocalDate lastDate = LocalDate.of(2022, 11, 13);
        String content = objectMapper.writeValueAsString(new DailyPostCountRequest(memberId, firstDate, lastDate));

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/daily-post-counts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
        ).andDo(print());

    }
}
