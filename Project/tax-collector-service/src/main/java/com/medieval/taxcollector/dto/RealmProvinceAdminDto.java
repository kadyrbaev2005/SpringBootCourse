package com.medieval.taxcollector.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealmProvinceAdminDto {

    private Long provinceId;
    private String provinceName;
    private BigDecimal revoltRisk;
    private long peasantCount;
}
