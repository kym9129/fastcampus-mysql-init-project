package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    /**
     * 이 부분에서 트랜잭션을 걸 지는 고민이 필요함
     * 팔로워가 많아질 경우 그만큼 트랜잭션이 길어지게 됨 -> undo log를 유지해야 하는 시간이 그만큼 길어짐
     * lock이라도 잡게 되면 lock 점유 시간이 길어지기 때문에 성능에 안좋은 영향 줄 수 있음.
     * ** 트랜잭션 사용 시 트랜잭션 범위를 최대한 짧게 가져가는 것이 좋다 **
     * 외부 요인에 의해 트랜잭션이 길어지게 되는 경우도 최대한 배제해야 함. (ex. 트랜잭션 도중 S3에 이미지 업로드)
     * 트랜잭션이 길어질 경우 DB의 커넥션을 오래 점유하게 됨.
     * 이런 일이 동시다발적으로 일어날 경우 커넥션풀이 고갈될 수 있음
     */
    public Long execute(PostCommand postCommand) {
        Long postId = postWriteService.create(postCommand);
        List<Long> followMemberIds = followReadService.getFollowers(postCommand.memberId())
                .stream()
                .map(FollowDto::fromMemberId)
                .toList();
        timelineWriteService.deliveryToTimeline(postId, followMemberIds);

        return postId;
    }
}
