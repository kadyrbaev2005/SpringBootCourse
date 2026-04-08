package com.medieval.taxcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.medieval.taxcollector.client")
public class TaxCollectorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxCollectorServiceApplication.class, args);
    }
}
