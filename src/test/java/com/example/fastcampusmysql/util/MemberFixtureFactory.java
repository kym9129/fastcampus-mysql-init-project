package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LocalDateRangeRandomizer;
import org.jeasy.random.randomizers.range.LocalDateTimeRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class MemberFixtureFactory {
    // 테스트 코드 작성 시 허들 중 하나 : 테스트 객체를 어떻게 생성할 것인가
    // 테스트코드의 케이스마다 값들이 다양해질 수 있다
    // -> fixture 라이브러리를 많이 사용한다. (ex. easy-random)

    public static Member create(){
        return MemberFixtureFactory.get().nextObject(Member.class);
    }

    public static EasyRandom get() {
        EasyRandomParameters param = new EasyRandomParameters()
//                .excludeField(idPredicate) // id is null
                .randomize(Long.class, new LongRangeRandomizer(1L, Long.MAX_VALUE))
                .stringLengthRange(5, 10)
//                .randomize(LocalDateTime.class, new LocalDateTimeRandomizer())
                .randomize(LocalDate.class, new LocalDateRangeRandomizer(LocalDate.of(1900, 1, 1), LocalDate.of(2015, 12,31)))
                .randomize(LocalDateTime.class, new LocalDateTimeRangeRandomizer(LocalDateTime.of(2023, 1, 1, 0, 0), LocalDateTime.of(2023,4,30, 23, 59)))
                .randomize(field -> field.getName().equals("updatedAt"), new LocalDateTimeRangeRandomizer(LocalDateTime.of(2023, 5, 1, 0, 0), LocalDateTime.of(2023,7,30, 23, 59)))
                ;

        return new EasyRandom(param);
    }

    public static Member create(Long seed){ // todo: 시드도 factory 내에서 랜덤하게 생성
        EasyRandomParameters param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static List<MemberNicknameHistory> getMemberNicknameHistoryList(Long memberId, int size) {
        // memberId는 파라미터 받은거 고정으로 넣고 id, nickname, createdAt을 랜덤으로 넣기
        Predicate<Field> idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(MemberNicknameHistory.class));

        Predicate<Field> memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(MemberNicknameHistory.class));

        EasyRandomParameters param = new EasyRandomParameters()
                .excludeField(idPredicate) // id null
                .randomize(memberIdPredicate, () -> memberId); // memberId 주입받은걸로 변환

        EasyRandom easyRandom = new EasyRandom(param);
        List<MemberNicknameHistory> memberNicknameHistoryList = new ArrayList<>();
        for (int i=0; i<size; i++){
            memberNicknameHistoryList.add(easyRandom.nextObject(MemberNicknameHistory.class));
        }

        return memberNicknameHistoryList;
    }
}
