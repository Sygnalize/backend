package com.echo.crypto.repository;

import com.echo.crypto.entities.Candlestick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandlestickRepository extends JpaRepository<Candlestick, Long> {
}
