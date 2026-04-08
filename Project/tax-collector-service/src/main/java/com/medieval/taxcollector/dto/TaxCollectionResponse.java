package com.medieval.taxcollector.dto;

import com.medieval.taxcollector.domain.KingMood;
import com.medieval.taxcollector.domain.Weather;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxCollectionResponse {

    private Long taxRecordId;
    private Long peasantId;
    private Long provinceId;
    private BigDecimal collectedAmount;
    private BigDecimal newPeasantBalance;
    private BigDecimal provinceRevoltRisk;
    private KingMood kingMoodUsed;
    private Weather weatherUsed;
    private BigDecimal moodMultiplier;
    private boolean oracleFallbackUsed;
}
