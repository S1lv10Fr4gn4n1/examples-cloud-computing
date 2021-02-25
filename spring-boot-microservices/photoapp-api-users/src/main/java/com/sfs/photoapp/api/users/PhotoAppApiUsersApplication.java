package com.sfs.photoapp.api.users;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class PhotoAppApiUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppApiUsersApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    Logger.Level defaultFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Profile("production")
    Logger.Level productionFeignLoggerLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    @Profile("production")
    public String createProductionBean() {
        System.out.println(">>>> Production Bean");
        return "Production Bean";
    }

    @Bean
    @Profile("!production")
    public String createNotProductionBean() {
        System.out.println(">>>> Not Production Bean");
        return "Not Production Bean";
    }
}
