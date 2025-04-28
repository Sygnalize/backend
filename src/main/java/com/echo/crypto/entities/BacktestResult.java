package com.echo.crypto.entities;

import com.echo.crypto.entities.Profile;
import com.echo.crypto.entities.Strategy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "backtest_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String resultData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
