package com.example.board2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Board2Application {

    public static void main(String[] args) {
        SpringApplication.run(Board2Application.class, args);
    }

}