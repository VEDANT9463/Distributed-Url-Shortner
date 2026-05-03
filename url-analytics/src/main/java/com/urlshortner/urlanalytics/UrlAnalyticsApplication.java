package com.urlshortner.urlanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class UrlAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlAnalyticsApplication.class, args);
    }

}
