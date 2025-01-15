package com.tennis.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.tennis.platform.model")
@EnableJpaRepositories("com.tennis.platform.repository")
public class TennisPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(TennisPlatformApplication.class, args);
    }
} 