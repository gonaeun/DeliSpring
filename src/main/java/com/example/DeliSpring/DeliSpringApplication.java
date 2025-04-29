package com.example.DeliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeliSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliSpringApplication.class, args);
    }

}
