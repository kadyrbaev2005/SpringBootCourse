package com.medieval.oracle.controller;

import com.medieval.oracle.client.openweather.OpenWeatherService;
import com.medieval.oracle.client.service.OracleService;
import com.medieval.oracle.domain.KingMood;
import com.medieval.oracle.domain.Weather;
import com.medieval.oracle.dto.OracleStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/oracle", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OracleController {

    private final OpenWeatherService openWeatherService;
    private final OracleService oracleService;

    @GetMapping("/status")
    public OracleStatusDto status() {
        Weather weather = openWeatherService.getWeatherTypes();
        KingMood mood = oracleService.getKingMood();
        return OracleStatusDto.builder()
                .weather(weather)
                .kingMood(mood)
                .build();
    }
}
