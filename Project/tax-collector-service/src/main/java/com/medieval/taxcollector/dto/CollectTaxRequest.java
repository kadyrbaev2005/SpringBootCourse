package com.medieval.taxcollector.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectTaxRequest {

    @NotNull
    private Long peasantId;

    @NotNull
    @Positive
    private BigDecimal baseTaxAmount;
}
