package com.dpbr.dpbrbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class DpbrBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpbrBeApplication.class, args);
    }

}
