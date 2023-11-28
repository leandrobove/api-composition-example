package com.github.leandrobove.apicomposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync

@SpringBootApplication
public class ApiCompositionExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCompositionExampleApplication.class, args);
    }

}
