package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberJdbcRepository;
import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("local")
public class MemberBulkInsertTest {
    @Autowired
    private MemberJdbcRepository memberJdbcRepository;

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = MemberFixtureFactory.get();

        int _1만 = 10000;
        List<Member> members = IntStream.range(0, _1만 * 100)
                .parallel() // 병렬로 실행
                .mapToObj(i -> easyRandom.nextObject(Member.class))
                .toList();

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        memberJdbcRepository.bulkInsert(members);

        queryStopWatch.stop();
        System.out.println("DB INSERT 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }


}
