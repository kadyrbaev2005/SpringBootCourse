package com.medieval.oracle.client.openweather;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "openweather-service",
        url = "${openweather.service.url}")
public interface OpenWeatherClient {

    @GetMapping("/data/2.5/forecast")
    JsonNode getForecast(
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon,
            @RequestParam("appid") String appid
    );
}
