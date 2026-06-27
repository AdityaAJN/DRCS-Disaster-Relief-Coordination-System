package com.drcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Application Class for Disaster Relief Coordination System (DRCS).
 * Bootstrap entry point for Spring Boot application runtime.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class DrcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrcsApplication.class, args);
    }
}