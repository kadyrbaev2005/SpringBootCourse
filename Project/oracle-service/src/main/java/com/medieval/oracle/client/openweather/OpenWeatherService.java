package com.medieval.oracle.client.openweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.medieval.oracle.domain.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class OpenWeatherService {

    private final OpenWeatherClient client;

    @Value("${openweather.api-key}")
    private String apiKey;

    public Weather getWeatherTypes() {
        JsonNode root = client.getForecast(
                "43.2363924",
                "76.9457275",
                apiKey
        );

        JsonNode firstItem = root.get("list").get(0);

        String main = firstItem
                .get("weather")
                .get(0)
                .get("main")
                .asText();

        return Weather.valueOf(main.toUpperCase());
    }
}
