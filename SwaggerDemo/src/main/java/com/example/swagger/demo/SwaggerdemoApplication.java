package com.example.swagger.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SwaggerdemoApplication {
    private static Logger logger = LoggerFactory.getLogger(SwaggerdemoApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(SwaggerdemoApplication.class, args);
    }

}

