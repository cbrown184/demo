package com.example.demo;

import com.example.demo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@Slf4j
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EventService service) {
        return args -> {
            service.processEntryPairs(new ArrayList<>( service.processJson(args[0]).values()));
            log.info("JSON processing finished - access DB at http://localhost:8080/h2-console/");
            Thread.currentThread().join();
        };

    }
}
