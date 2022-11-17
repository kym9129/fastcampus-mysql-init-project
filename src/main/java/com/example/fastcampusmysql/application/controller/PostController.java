package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @PostMapping("")
    public Long create(PostCommand command) {
        return postWriteService.create(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(
            @RequestParam Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate
            ) {
        DailyPostCountRequest request = new DailyPostCountRequest(memberId, firstDate, lastDate);
        return postReadService.getDailyPostCounts(request);
    }

    // 스웨거로 호출하면 "Required request body is missing" 에러 발생.
    // 테스트코드로 호출할 땐 requestBody값 잘 들어오는 걸로 보아
    // 스웨거에서 호출할 때 accept나 contentType쪽에서 뭔가 문제가 있지 않을까 싶은?
    // curl -X 'GET' \
    //  'http://localhost:8080/posts/daily-post-counts?memberId=2&firstDate=2022-11-13&lastDate=2022-11-13' \
    //  -H 'accept: */*' <- 이게 관련있나?
//    @GetMapping("/daily-post-counts")
//    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request){
//        log.info("request = {}", request);
//        return postReadService.getDailyPostCounts(request);
//    }
}
