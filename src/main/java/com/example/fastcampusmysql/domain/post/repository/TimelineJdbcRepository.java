package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimelineJdbcRepository {
    private final static String TABLE = "timeline";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void bulkInsert(List<Timeline> timelines) {
        String sql = String.format("""
                    INSERT INTO `%s` (member_id, post_id, created_at, updated_at)
                    VALUES (:memberId, :postId, NOW(), NOW())
                    """, TABLE);

        SqlParameterSource[] params = timelines
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
