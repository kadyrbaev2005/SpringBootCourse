package com.medieval.oracle.client.service;

import com.medieval.oracle.client.goldapi.GoldApiService;
import com.medieval.oracle.domain.KingMood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OracleService {

    private final GoldApiService goldService;

    public KingMood getKingMood(){
        Double goldPriceYesterday = goldService.getGoldPriceYesterday();
        Double goldPriceTwoDaysAgo = goldService.getGoldPriceTwoDaysAgo();
        if (goldPriceYesterday > goldPriceTwoDaysAgo){
            return KingMood.HAPPY;
        }
        else if (goldPriceYesterday < goldPriceTwoDaysAgo){
            return KingMood.ENRAGED;
        }
        else{
            return KingMood.NEUTRAL;
        }
    }
}
