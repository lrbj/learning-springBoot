package com.example.swagger.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SwaggerdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerdemoApplication.class, args);
    }

}

