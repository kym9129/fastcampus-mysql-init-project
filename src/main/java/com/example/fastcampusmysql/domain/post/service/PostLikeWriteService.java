package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.PostLike;
import com.example.fastcampusmysql.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.redis.key.post_like_prefix}")
    private String POST_LIKE_KEY_PREFIX;

    public Long create(Post post, MemberDto memberDto) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.add(POST_LIKE_KEY_PREFIX+post.getId(), String.valueOf(memberDto.id()));

        PostLike postLike = PostLike
                .builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();
        return postLikeRepository.save(postLike).getPostId();
    }


}
