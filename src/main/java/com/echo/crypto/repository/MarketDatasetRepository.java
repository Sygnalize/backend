package com.echo.crypto.repository;

import com.echo.crypto.entities.MarketDataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MarketDatasetRepository extends JpaRepository<MarketDataset, Long> {
    Optional<MarketDataset> findBySymbolAndFromDateAndToDate(String symbol, LocalDate fromDate, LocalDate toDate);
}
