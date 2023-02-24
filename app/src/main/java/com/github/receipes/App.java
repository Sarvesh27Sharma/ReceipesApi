package com.github.receipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.receipes")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
