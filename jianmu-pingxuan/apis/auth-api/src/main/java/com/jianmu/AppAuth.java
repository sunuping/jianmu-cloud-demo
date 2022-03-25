package com.jianmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AppAuth {
    public static void main(String[] args) {
        SpringApplication.run(AppAuth.class, args);
    }
}
