package com.example.SIS2.services;

import com.example.SIS2.dto.JokeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, String> getExternalData() {

        String url = "https://official-joke-api.appspot.com/random_joke";

        JokeResponse response =
                restTemplate.getForObject(url, JokeResponse.class);

        return Map.of(
                "source", "official-joke-api.appspot",
                "value", response.getSetup()
        );
    }
}