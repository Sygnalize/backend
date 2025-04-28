package com.echo.crypto.dto;

import java.time.LocalDate;

public record MarketDatasetShortDto(
        Long id,
        String symbol,
        LocalDate fromDate,
        LocalDate toDate
) {}
