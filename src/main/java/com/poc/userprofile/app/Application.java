package com.poc.userprofile.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.poc.userprofile" )
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
