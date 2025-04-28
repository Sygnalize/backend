package com.echo.crypto.dto;

import java.math.BigDecimal;

public record CandlestickDto(
        Long id,
        Long timestamp,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume
) {}
