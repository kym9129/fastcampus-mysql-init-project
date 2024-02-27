package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberJdbcRepository {
    private final static String TABLE = "member";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void bulkInsert(List<Member> members) {
        String sql = String.format("""
                    INSERT INTO `%s` (email, nickname, birthday, created_at, updated_at)
                    VALUES (:email, :nickname, :birthday, NOW(), NOW())
                    """, TABLE);

        SqlParameterSource[] params = members
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
