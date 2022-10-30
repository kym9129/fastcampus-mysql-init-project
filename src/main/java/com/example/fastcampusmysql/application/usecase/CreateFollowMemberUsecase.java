package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {
    // 이 기능 하나만 실행할거라 클래스명을 동사로 썼음

    /**
     * read write를 분리해서 얻는 이점
     * 이 usecase 클래스는 회원 도메인을 참조하지만 회원에 대한 쓰기 권한이 전혀 없다.
     */
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    /**
     * 1. 입력받은 memberId로 회원 조회
     * 2. FollowWriteService.create()
     *
     * usecase레이어는 가능한 로직이 없어야 한다.
     * 각각 도메인에 대한 비즈니스로직은 각각의 도메인 안에 들어가있어야 하고,
     * usecase는 `각 도메인 서비스의 흐름을 제어`하는 역할을 해야한다.
     */
    public void execute(Long fromMemberId, Long toMemberId) {
        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }
}
