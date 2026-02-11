package com.example.SIS2.controllers;

import com.example.SIS2.services.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DataController {

    private final ExternalApiService externalApiService;

    // Constructor Injection
    public DataController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/api/data")
    public Map<String, String> getData() {
        return externalApiService.getExternalData();
    }
}
