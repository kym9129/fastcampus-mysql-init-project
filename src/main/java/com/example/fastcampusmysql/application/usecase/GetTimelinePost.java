package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// post, follow 2가지 도메인을 사용해야 하므로 usecase를 생성했음
@Service
@RequiredArgsConstructor
public class GetTimelinePost {
    private final FollowReadService followReadService;
    private final PostReadService postReadService;

    /**
     * 1. memberId로 follow 정보 조회
     * 2. 1번의 결과로 게시물을 조회
     * @param memberId
     * @param cursorRequest 무한스크롤로 구현. 커서 기반 페이징
     */
    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        List<FollowDto> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(FollowDto::toMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }
}
