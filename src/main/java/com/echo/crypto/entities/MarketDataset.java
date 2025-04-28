package com.echo.crypto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market_datasets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    private LocalDate fromDate;
    private LocalDate toDate;

    @OneToMany(mappedBy = "marketDataset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candlestick> candles = new ArrayList<>();
}
