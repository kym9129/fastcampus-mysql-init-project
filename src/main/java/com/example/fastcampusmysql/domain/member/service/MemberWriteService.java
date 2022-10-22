package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService { // 읽기 쓰기 분리를 위해 Write/Read Service 분리하였음
    private final MemberRepository memberRepository;

    /**
     * 회원 정보(이메일, 닉네임, 생년월일) 등록
     */
    public MemberDto register(RegisterMemberCommand command){
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        return toDto(memberRepository.save(member));
    }

    public MemberDto toDto(Member member){
        // 객체 간 맵핑 로직은 mapper 레이어를 두기도 하지만, 실습 플젝은 사이즈가 작아서 service에서 처리
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }
}
