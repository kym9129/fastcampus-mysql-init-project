package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

        List<Follow> findAllByFromMemberId(Long fromMemberId);
        List<Follow> findAllByToMemberId(Long toMemberId);

}
