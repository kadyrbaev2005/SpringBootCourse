package com.medieval.taxcollector.client.dto;

import com.medieval.taxcollector.domain.KingMood;
import com.medieval.taxcollector.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OracleStatusDto {

    private KingMood kingMood;
    private Weather weather;
}
