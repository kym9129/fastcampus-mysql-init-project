package com.example.fastcampusmysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FastcampusMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastcampusMysqlApplication.class, args);
    }

}
