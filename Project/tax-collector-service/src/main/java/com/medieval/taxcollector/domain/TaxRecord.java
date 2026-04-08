package com.medieval.taxcollector.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "collected_at", nullable = false)
    private Instant collectedAt;

    @Column(name = "king_mood_snapshot", nullable = false, length = 32)
    private String kingMoodSnapshot;

    @Column(name = "weather_snapshot", nullable = false, length = 32)
    private String weatherSnapshot;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "peasant_id", nullable = false)
    private Peasant peasant;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;
}
