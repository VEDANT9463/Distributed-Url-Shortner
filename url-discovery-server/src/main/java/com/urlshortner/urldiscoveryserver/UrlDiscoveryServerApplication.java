package com.urlshortner.urldiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UrlDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlDiscoveryServerApplication.class, args);
    }

}
