package com.jianmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppVote {
    public static void main(String[] args) {
        SpringApplication.run(AppVote.class, args);
    }
}
