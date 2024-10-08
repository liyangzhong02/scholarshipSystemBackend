package com.marre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SchlorshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchlorshipApplication.class, args);
        log.info("Server started");
    }

}
