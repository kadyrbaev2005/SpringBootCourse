package com.medieval.oracle.service;

import com.medieval.oracle.domain.KingMood;
import com.medieval.oracle.domain.Weather;
import com.medieval.oracle.dto.OracleStatusDto;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class OracleStatusService {

    private static final List<KingMood> MOODS = List.copyOf(EnumSet.allOf(KingMood.class));
    private static final List<Weather> WEATHERS = List.copyOf(EnumSet.allOf(Weather.class));

    public OracleStatusDto readPortents() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        KingMood mood = MOODS.get(rnd.nextInt(MOODS.size()));
        Weather weather = resolveWeather(mood, rnd);
        return OracleStatusDto.builder()
                .kingMood(mood)
                .weather(weather)
                .build();
    }

    private Weather resolveWeather(KingMood mood, ThreadLocalRandom rnd) {
        return switch (mood) {
            case ENRAGED -> rnd.nextDouble() < 0.55 ? Weather.STORM : pickNonStorm(rnd);
            case HAPPY -> rnd.nextDouble() < 0.6 ? Weather.SUNNY : Weather.RAINY;
            case NEUTRAL -> WEATHERS.get(rnd.nextInt(WEATHERS.size()));
        };
    }

    private Weather pickNonStorm(ThreadLocalRandom rnd) {
        return rnd.nextBoolean() ? Weather.SUNNY : Weather.RAINY;
    }
}
