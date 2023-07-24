package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.fastcampusmysql.domain.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        return queryFactory
                .select(Projections.constructor(DailyPostCount.class
                        , post.memberId
                        , post.createdDate.as("date")
                        , post.id.count().as("postCount")
                ))
                .from(post)
                .where(post.memberId.eq(request.memberId())
                    .and(post.createdDate.between(request.firstDate(), request.lastDate()))
                )
                .groupBy(post.createdDate, post.memberId)
                .fetch();
    }
}
