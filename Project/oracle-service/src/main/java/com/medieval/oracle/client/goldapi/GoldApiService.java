package com.medieval.oracle.client.goldapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.medieval.oracle.domain.KingMood;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public final class GoldApiService {

    private final GoldApiClient client;

    @Value("${GOLD_API_KEY}")
    private String apikey;

    public Double getGoldPriceYesterday(){
        String date = LocalDate.now().minusDays(1).toString();
        JsonNode root = client.getGoldPrice(date, apikey);

        return root.get("price").asDouble();
    }

    public Double getGoldPriceTwoDaysAgo(){
        String date = LocalDate.now().minusDays(2).toString();
        JsonNode root = client.getGoldPrice(date, apikey);

        return root.get("price").asDouble();
    }
}
