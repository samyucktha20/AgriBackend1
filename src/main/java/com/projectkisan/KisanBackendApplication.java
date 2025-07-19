package com.projectkisan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableAsync
public class KisanBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(KisanBackendApplication.class, args);
    }
}