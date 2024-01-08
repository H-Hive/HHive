package com.HHive.hhive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HhiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhiveApplication.class, args);
    }

}
