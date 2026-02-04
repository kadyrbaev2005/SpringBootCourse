package com.example.sis1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/api/info")
    public Map<String, Object> getInfo() {
        return Map.of(
                "student", "Kadyrbayev Akzhol",
                "course", "Spring Boot",
                "week", 1
        );
    }
}
