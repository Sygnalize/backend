package com.echo.crypto.mapper;

import com.echo.crypto.dto.CandlestickDto;
import com.echo.crypto.entities.Candlestick;

public class CandlestickMapper {

    public static CandlestickDto toDto(Candlestick candlestick) {
        return new CandlestickDto(
                candlestick.getId(),
                candlestick.getTimestamp(),
                candlestick.getOpen(),
                candlestick.getHigh(),
                candlestick.getLow(),
                candlestick.getClose(),
                candlestick.getVolume()
        );
    }

    public static Candlestick toEntity(CandlestickDto candlestickDto) {
        Candlestick candlestick = new Candlestick();
        candlestick.setId(candlestickDto.id());
        candlestick.setTimestamp(candlestickDto.timestamp());
        candlestick.setOpen(candlestickDto.open());
        candlestick.setHigh(candlestickDto.high());
        candlestick.setLow(candlestickDto.low());
        candlestick.setClose(candlestickDto.close());
        candlestick.setVolume(candlestickDto.volume());
        return candlestick;
    }
}
