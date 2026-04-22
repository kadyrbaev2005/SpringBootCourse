package com.medieval.taxcollector.service;

import com.medieval.taxcollector.client.OracleClient;
import com.medieval.taxcollector.client.OracleFallbackContext;
import com.medieval.taxcollector.client.dto.OracleStatusDto;
import com.medieval.taxcollector.domain.KingMood;
import com.medieval.taxcollector.domain.Peasant;
import com.medieval.taxcollector.domain.Province;
import com.medieval.taxcollector.domain.TaxRecord;
import com.medieval.taxcollector.domain.Weather;
import com.medieval.taxcollector.dto.CollectTaxRequest;
import com.medieval.taxcollector.dto.TaxCollectionResponse;
import com.medieval.taxcollector.repository.PeasantRepository;
import com.medieval.taxcollector.repository.ProvinceRepository;
import com.medieval.taxcollector.repository.TaxRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaxService {

    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final BigDecimal HIGH_BURDEN_RATIO = new BigDecimal("0.35");
    private static final BigDecimal REVOLT_SPIKE = new BigDecimal("7.00");
    private static final BigDecimal STORM_REVOLT_TICK = new BigDecimal("1.25");

    private final OracleClient oracleClient;
    private final PeasantRepository peasantRepository;
    private final ProvinceRepository provinceRepository;
    private final TaxRecordRepository taxRecordRepository;

    @Transactional
    public TaxCollectionResponse collectTax(CollectTaxRequest request) {
        OracleStatusDto portents = oracleClient.getStatus();
        boolean oracleFallbackUsed = OracleFallbackContext.pollFallbackUsed();

        BigDecimal moodMultiplier = moodMultiplier(portents.getKingMood());
        BigDecimal weatherAdj = weatherTaxAdjustment(portents.getWeather());

        BigDecimal base = request.getBaseTaxAmount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal collected = base.multiply(moodMultiplier).multiply(weatherAdj).setScale(2, RoundingMode.HALF_UP);

        Peasant peasant = peasantRepository
                .findById(request.getPeasantId())
                .orElseThrow(() -> new EntityNotFoundException("Peasant not found: " + request.getPeasantId()));

        BigDecimal balanceBefore = peasant.getBalance();
        if (balanceBefore.compareTo(collected) < 0) {
            throw new IllegalStateException(
                    "Peasant cannot pay the crown: required " + collected + " but holds " + balanceBefore);
        }

        peasant.setBalance(balanceBefore.subtract(collected));

        Province province = peasant.getProvince();
        applyRevoltPressure(province, balanceBefore, collected, portents.getWeather());

        TaxRecord record = TaxRecord.builder()
                .amount(collected)
                .collectedAt(Instant.now())
                .kingMoodSnapshot(String.valueOf(portents.getKingMood()))
                .weatherSnapshot(String.valueOf(portents.getWeather()))
                .peasant(peasant)
                .province(province)
                .build();

        TaxRecord saved = taxRecordRepository.save(record);
        peasantRepository.save(peasant);
        provinceRepository.save(province);

        return TaxCollectionResponse.builder()
                .taxRecordId(saved.getId())
                .peasantId(peasant.getId())
                .provinceId(province.getId())
                .collectedAmount(collected)
                .newPeasantBalance(peasant.getBalance())
                .provinceRevoltRisk(province.getRevoltRisk())
                .kingMoodUsed(portents.getKingMood())
                .weatherUsed(portents.getWeather())
                .moodMultiplier(moodMultiplier)
                .oracleFallbackUsed(oracleFallbackUsed)
                .build();
    }

    private static BigDecimal moodMultiplier(KingMood mood) {
        if (mood == null) {
            return BigDecimal.ONE;
        }
        return switch (mood) {
            case HAPPY -> new BigDecimal("0.85");
            case NEUTRAL -> BigDecimal.ONE;
            case ENRAGED -> new BigDecimal("2.00");
        };
    }

    private static BigDecimal weatherTaxAdjustment(Weather weather) {
        if (weather == null) {
            return BigDecimal.ONE;
        }
        return switch (weather) {
            case THUNDERSTORM -> new BigDecimal("2.00");
            case DRIZZLE -> new BigDecimal("1.80");
            case RAIN -> new BigDecimal("1.60");
            case SNOW -> new BigDecimal("1.40");
            case ATMOSPHERE -> new BigDecimal("1.20");
            case CLOUDS -> new BigDecimal("1.00");
            case CLEAR -> new BigDecimal("0.8");
        };
    }

    private void applyRevoltPressure(Province province, BigDecimal balanceBefore, BigDecimal collected, Weather weather) {
        if (balanceBefore.signum() <= 0) {
            return;
        }
        BigDecimal burdenRatio = collected.divide(balanceBefore, 4, RoundingMode.HALF_UP);
        BigDecimal risk = province.getRevoltRisk();
        if (burdenRatio.compareTo(HIGH_BURDEN_RATIO) > 0) {
            risk = risk.add(REVOLT_SPIKE);
        }
        if (weather == Weather.THUNDERSTORM) {
            risk = risk.add(STORM_REVOLT_TICK);
        }
        if (risk.compareTo(HUNDRED) > 0) {
            risk = HUNDRED;
        }
        province.setRevoltRisk(risk.setScale(2, RoundingMode.HALF_UP));
    }
}
