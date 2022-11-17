package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.utill.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                2L,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 2, 1)
        );

        // 5건 insert 해보기
        StopWatch stopWatch = new StopWatch();
//        IntStream.range(0, 1000000)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(post ->
//                        postRepository.save(post)
//                );
//        stopWatch.stop();
//        System.out.println("객체 생성 및 DB INSERT 시간 : " + stopWatch.getTotalTimeSeconds());

        // 배치 돌릴 때는 JDBC로 벌크 인서트 자주 쓴다고 함
        // (Spring Data JPA의 saveAll은 save를 loop 돌려서 사용하므로 벌크인서트가 아님)

        stopWatch.start();
        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 100)
                .parallel() // 병렬로 실행
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();


        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB INSERT 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
