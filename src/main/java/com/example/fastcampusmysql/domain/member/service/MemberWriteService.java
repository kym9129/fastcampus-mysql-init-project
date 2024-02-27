package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberJdbcRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberWriteService { // 읽기 쓰기 분리를 위해 Write/Read Service 분리하였음
    private final MemberRepository memberRepository;
    private final MemberJdbcRepository memberJdbcRepository; // todo: 리펙토링할 방법 찾기
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    /**
     * 회원 정보(이메일, 닉네임, 생년월일) 등록
     * 히스토리 저장
     */
    @Transactional
    public MemberDto register(RegisterMemberCommand command){
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();

        Member saveMember = memberRepository.save(member);
        saveMemberNicknameHistory(saveMember);

        return toDto(saveMember);
    }

    /**
     * 1. 회원의 이름을 변경
     * 2. 변경 내역을 저장
     * @param memberId
     * @param nickname
     */
    public void changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .member(member)
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }

    public MemberDto toDto(Member member){
        // 객체 간 맵핑 로직은 mapper 레이어를 두기도 하지만, 실습 플젝은 사이즈가 작아서 service에서 처리
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    public void bulkInsert(Long amount) {
        List<Member> members = new ArrayList<>();
        for(long i=0L; i<amount; i++){
            int id = LocalDateTime.now().getNano();
            String nickname = String.valueOf(id).length() >= 10 ? String.valueOf(id).substring(0, 10) : String.valueOf(id);
            Member member = Member.builder()
                    .email(id + "@mail.com")
                    .nickname(nickname)
                    .birthday(LocalDate.now())
                    .build();

            members.add(member);
        }

        memberJdbcRepository.bulkInsert(members);
    }
}
