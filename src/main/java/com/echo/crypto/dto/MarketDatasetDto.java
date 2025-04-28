package com.echo.crypto.dto;

import java.time.LocalDate;
import java.util.List;

public record MarketDatasetDto(
        Long id,
        String symbol,
        LocalDate fromDate,
        LocalDate toDate,
        List<CandlestickDto> candles
) {}
