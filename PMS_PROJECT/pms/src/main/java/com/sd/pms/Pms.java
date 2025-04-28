package com.sd.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class Pms {

    public static void main(String[] args) {
        SpringApplication.run(Pms.class, args);
    }

}
