package com.medieval.taxcollector.client;

import com.medieval.taxcollector.client.dto.OracleStatusDto;
import com.medieval.taxcollector.domain.KingMood;
import com.medieval.taxcollector.domain.Weather;
import org.springframework.stereotype.Component;

@Component
public class OracleClientFallback implements OracleClient {

    @Override
    public OracleStatusDto getStatus() {
        OracleFallbackContext.markFallbackUsed();
        return OracleStatusDto.builder()
                .kingMood(KingMood.NEUTRAL)
                .weather(Weather.SUNNY)
                .build();
    }
}
