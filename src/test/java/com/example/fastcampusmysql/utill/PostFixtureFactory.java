package com.example.fastcampusmysql.utill;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {

    // MemberFixtureFactory에서는 EasyRandom 인스턴스를 생성하고 랜덤 멤버객체를 만들어서 그걸 return함
    // 이번 실습은 100만건 insert이기 때문에 MemberFixtureFactory와 같은 구조로 Post를 리턴할 경우 100만개의 이지랜덤 인스턴스가 생성되어야 함
    // 따라서 이번엔 EasyRandom 객체를 반환하는 메소드를 만들고 외부에서 .nextObject()를 반복호출하는 방식으로 진행
    public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {
        Predicate<Field> idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        Predicate<Field> memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        EasyRandomParameters param = new EasyRandomParameters()
                .excludeField(idPredicate) // id : null 반환
                .dateRange(firstDate, lastDate) // createDate : firstDate, lastDate 사이의 랜덤값 반환
                .randomize(memberIdPredicate, () -> memberId); // memberId : 주입받은 memberId 반환

        return new EasyRandom(param);
    }
}
