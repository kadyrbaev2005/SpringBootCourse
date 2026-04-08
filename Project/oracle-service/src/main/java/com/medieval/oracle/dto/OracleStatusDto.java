package com.medieval.oracle.dto;

import com.medieval.oracle.domain.KingMood;
import com.medieval.oracle.domain.Weather;
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
