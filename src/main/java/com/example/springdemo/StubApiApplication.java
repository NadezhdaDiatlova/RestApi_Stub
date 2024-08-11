package com.example.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StubApiApplication {

    public static void main(String[] args) { //запускает приложение, вызывая метод run у SpringApplication, что инициализирует контекст приложения.
        SpringApplication.run(StubApiApplication.class, args);
    }

}
