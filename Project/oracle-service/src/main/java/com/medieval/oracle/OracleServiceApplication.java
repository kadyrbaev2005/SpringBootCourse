package com.medieval.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OracleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OracleServiceApplication.class, args);
    }
}
