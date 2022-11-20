package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Timeline {
    private final Long id;
    private final Long memberId;
    private final Long postId;
    private final LocalDateTime createdAt;

    @Builder
    public Timeline(Long id, Long memberId, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.postId = Objects.requireNonNull(postId);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        // 좀 더 엄격하게 하려면 데이터 생성 시점에만 createdAt이 들어가니까 id가 null일 때만 now()가 되도록 할 수도 있음.
    }
}
