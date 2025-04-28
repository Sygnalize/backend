package com.echo.crypto.mapper;

import com.echo.crypto.dto.MarketDatasetDto;
import com.echo.crypto.dto.MarketDatasetShortDto;
import com.echo.crypto.entities.MarketDataset;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MarketDatasetMapper {

    public MarketDatasetDto toDto(MarketDataset dataset) {
        return new MarketDatasetDto(
                dataset.getId(),
                dataset.getSymbol(),
                dataset.getFromDate(),
                dataset.getToDate(),
                dataset.getCandles().stream()
                        .map(CandlestickMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public MarketDataset toEntity(MarketDatasetDto dto) {
        var entity = new MarketDataset();
        entity.setId(dto.id());
        entity.setSymbol(dto.symbol());
        entity.setFromDate(dto.fromDate());
        entity.setToDate(dto.toDate());
        entity.setCandles(dto.candles().stream()
                .map(CandlestickMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }

    public MarketDatasetShortDto toShortDto(MarketDataset dataset) {
        return new MarketDatasetShortDto(
                dataset.getId(),
                dataset.getSymbol(),
                dataset.getFromDate(),
                dataset.getToDate()
        );
    }

}
