package com.example.dishdash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DishDashApplication {

    public static void main(String[] args) {
        SpringApplication.run(DishDashApplication.class, args);
    }

}
