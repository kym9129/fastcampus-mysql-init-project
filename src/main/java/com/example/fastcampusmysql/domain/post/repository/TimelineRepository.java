package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findAllByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);
    List<Timeline> findAllByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long id, Pageable pageable);
}
