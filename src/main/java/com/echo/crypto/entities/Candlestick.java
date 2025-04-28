package com.echo.crypto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "candlesticks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candlestick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long timestamp;

    @Column(nullable = false)
    private BigDecimal open;

    @Column(nullable = false)
    private BigDecimal high;

    @Column(nullable = false)
    private BigDecimal low;

    @Column(nullable = false)
    private BigDecimal close;

    @Column(nullable = false)
    private BigDecimal volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_dataset_id")
    private MarketDataset marketDataset;
}
