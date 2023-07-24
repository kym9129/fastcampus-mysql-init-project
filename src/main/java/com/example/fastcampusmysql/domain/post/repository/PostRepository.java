package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Page<Post> findAllByMemberId(Long memberId, Pageable pageable);
    List<Post> findByIdIn(List<Long> ids);
    List<Post> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);
    List<Post> findByMemberIdInOrderByIdDesc(List<Long> memberIds, Pageable pageable);
    List<Post> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long id, Pageable pageable);

    List<Post> findAllByMemberIdInAndIdLessThanOrderByIdDesc(List<Long> memberIds, long id, Pageable pageable);
}
