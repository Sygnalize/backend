package com.echo.crypto.service;

import com.echo.crypto.dto.BybitKlineResponse;
import com.echo.crypto.dto.CandlestickDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CandlestickService {
    List<CandlestickDto> getCandlesticksForMarketDataset(String symbol, String interval, LocalDateTime from, LocalDateTime to);
    List<CandlestickDto> getCandlesticksForBacktest(Long backtestId);
    void deleteOutliersForDataset(Long datasetId);
    List<CandlestickDto> normalizeCandlesticks(Long datasetId);
    Map<String, BigDecimal> getCandlestickSummary(Long datasetId);
}

