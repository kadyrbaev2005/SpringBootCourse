package com.example.sis1.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CalculateController {

    @PostMapping("/api/calculate")
    public Map<String, Integer> calculate(@RequestBody Map<String, Integer> request) {
        int a = request.get("a");
        int b = request.get("b");

        int result = a + b; // операция: сложение

        return Map.of("result", result);
    }
}
