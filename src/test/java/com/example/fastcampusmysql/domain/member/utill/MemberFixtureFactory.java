package com.example.fastcampusmysql.domain.member.utill;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {
    // 테스트 코드 작성 시 허들 중 하나 : 테스트 객체를 어떻게 생성할 것인가
    // 테스트코드의 케이스마다 값들이 다양해질 수 있다
    // -> fixture 라이브러리를 많이 사용한다. (ex. easy-random)

    public static Member create(){
        EasyRandomParameters param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(Long seed){ // todo: 시드도 factory 내에서 랜덤하게 생성
        EasyRandomParameters param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
}
