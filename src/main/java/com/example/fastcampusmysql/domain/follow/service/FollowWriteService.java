package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class FollowWriteService {
    private final FollowRepository followRepository;

    /**
     * from, to 회원 정보를 받아서 저장
     * from <-> to validate
     *
     * [고민 포인트 : 멤버정보를 식별자로 받을까? Dto로 받을까?]
     * - 식별자로 받을 경우, 존재하는 사용자인지 검증을 여기서 해야함.
     * - MemberDto로 받는다면 어디서 넘겨줄까?
     *
     * 실무에서 자주 하는 고민들 - 서로 다른 도메인의 데이터를 주고받아야 할 때, 서로 다른 도메인의 흐름을 제어해야 할 때 어디서 해야하는가?
     *  - 경계를 나누는 방법들:  헥사고날 아키텍처, DDD, 레이어드 아키텍처, ...
     *  - 이번 클립은 대용량 서비스 다루니 심플하게 가보고자 함
     */
    public void create(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");

        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        followRepository.save(follow);
    }
}
