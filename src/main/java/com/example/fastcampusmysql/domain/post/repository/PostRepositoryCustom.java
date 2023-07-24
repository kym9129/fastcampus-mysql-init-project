package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;

import java.util.List;

public interface PostRepositoryCustom {
    List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request);
}
