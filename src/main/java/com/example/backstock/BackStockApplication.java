package com.example.backstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class BackStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackStockApplication.class, args);
        System.out.println("application started");
    }

}
