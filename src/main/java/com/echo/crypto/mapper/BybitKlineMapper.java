package com.echo.crypto.mapper;

import com.echo.crypto.dto.CandlestickDto;

import java.math.BigDecimal;
import java.util.List;

public class BybitKlineMapper {

    public static List<CandlestickDto> mapToCandlestickDtos(List<List<String>> raw) {
        return raw.stream()
                .map(item -> new CandlestickDto(
                        null,
                        Long.parseLong(item.get(0)),
                        new BigDecimal(item.get(1)),
                        new BigDecimal(item.get(2)),
                        new BigDecimal(item.get(3)),
                        new BigDecimal(item.get(4)),
                        new BigDecimal(item.get(5))
                ))
                .toList();
    }
}
