package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostJdbcRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("local") // -test는 H2디비 사용하기 떄문에 local로 설정
public class PostBulkInsertTest {
    @Autowired
    private PostJdbcRepository postJdbcRepository;

    @Test
    @Disabled
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1970, 1, 1),
                LocalDate.of(2022, 2, 1)
        );

        // 5건 insert 해보기
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        IntStream.range(0, 1000000)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(post ->
//                        postRepository.save(post)
//                );
//        stopWatch.stop();
//        System.out.println("객체 생성 및 DB INSERT 시간 : " + stopWatch.getTotalTimeSeconds());

        // 배치 돌릴 때는 JDBC로 벌크 인서트 자주 쓴다고 함
        // (Spring Data JPA의 saveAll은 save를 loop 돌려서 사용하므로 벌크인서트가 아님)

        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 200)
                .parallel() // 병렬로 실행
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postJdbcRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB INSERT 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
