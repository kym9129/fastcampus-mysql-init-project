package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.redis.key.post_like_prefix}")
    private String POST_LIKE_KEY_PREFIX;

    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPosts(long memberId, Pageable pageRequest) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return postRepository.findAllByMemberId(memberId, pageRequest)
                .map(post -> toDto(post, setOps)); // Page 인터페이스에 map이 있음
    }

    private PostDto toDto(Post post, SetOperations<String, String> setOps){
        Long likeCount = setOps.size(POST_LIKE_KEY_PREFIX + post.getId());
        return new PostDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                likeCount
        );
    }

    public PageCursor<PostDto> getPosts(long memberId, CursorRequest cursorRequest) {
        validateSize(cursorRequest.size());
        List<Post> posts = findAllBy(memberId, cursorRequest);
        // 반환한 데이터에서 가장 작은 key값을 추출. 없으면 NONE_KEY 리턴
        Long nextKey = getNextKey(posts);

        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        List<PostDto> postDtos = posts.stream().map(post -> toDto(post, setOps)).toList();
        return new PageCursor<>(cursorRequest.next(nextKey), postDtos);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findByIdIn(ids);
    }

    public Post getPost(Long postId){
        return postRepository.findById(postId).orElseThrow();
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        validateSize(cursorRequest.size());
        List<Post> posts = findAllBy(memberIds, cursorRequest);
        Long nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    private void validateSize(int size) {
        if(size == 0) {
            throw new IllegalArgumentException("size는 1 이상이어야 합니다.");
        }
    }

    private long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }

    private List<Post> findAllBy(long memberId, CursorRequest cursorRequest) {
        Pageable pageable = PageRequest.of(0, cursorRequest.size());
        if(cursorRequest.hasKey()) {
            return postRepository.findByMemberIdAndIdLessThanOrderByIdDesc(memberId, cursorRequest.key(), pageable);
        }
        return postRepository.findByMemberIdOrderByIdDesc(memberId, pageable);
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        Pageable pageable = PageRequest.of(0, cursorRequest.size());
        if(cursorRequest.hasKey()) {
            return postRepository.findAllByMemberIdInAndIdLessThanOrderByIdDesc(memberIds, cursorRequest.key(), pageable);
        }
        return postRepository.findByMemberIdInOrderByIdDesc(memberIds, pageable);
    }
}
