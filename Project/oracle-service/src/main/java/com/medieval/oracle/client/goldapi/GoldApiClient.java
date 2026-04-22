package com.medieval.oracle.client.goldapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "goldapi-service",
        url = "${goldapi.service.url}")
public interface GoldApiClient {

    @GetMapping("/{date}")
    JsonNode getGoldPrice(
            @PathVariable String date,
            @RequestHeader("x-access-token") String apiKey
    );
}
