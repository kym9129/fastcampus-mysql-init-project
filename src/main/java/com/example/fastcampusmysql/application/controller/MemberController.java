package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    // 고민포인트
    // JPA 사용 시 컨트롤러에까지 Entity가 나오면 (=컨트롤러가 Entity에 의존하면) OSIV 이슈가 발생할 수 있음
    // 레이어드 아키텍처에서 entity는 가장 깊은 곳에 있기 때문에, 프레젠테이션 레이어까지 entity가 나올 경우 프레젠테이션 레이어의 요구사항에 entity가 변경되는 사항이 생길 수 있다.
    // -> DTO를 사용하는 것으로 해결

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto register(@RequestBody RegisterMemberCommand command){
        return memberWriteService.register(command);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id){
        return memberReadService.getMember(id);
    }

    @PutMapping("/{id}/nickname")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }

    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistory(memberId);
    }

    @PostMapping("/bulk-insert/{amount}")
    public void bulkInsert(@PathVariable Long amount){
        memberWriteService.bulkInsert(amount);
    }
}
