package com.haorld.perfvs.sdemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdemoApplication.class, args);
        System.out.println("start demo with spring boot 3.5.5 at Java 21.0.8");
    }
}
