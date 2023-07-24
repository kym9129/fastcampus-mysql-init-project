package com.example.fastcampusmysql.domain.post.entity;

import com.example.fastcampusmysql.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity(name = "timeline")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Timeline extends BaseTimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "post_id")
    private Long postId;

    @Builder
    public Timeline(Long id, Long memberId, Long postId) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.postId = Objects.requireNonNull(postId);
    }
}
