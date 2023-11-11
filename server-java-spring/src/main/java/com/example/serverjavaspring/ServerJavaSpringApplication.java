package com.example.serverjavaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerJavaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerJavaSpringApplication.class, args);
    }

}
