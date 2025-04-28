package com.echo.crypto.controllers;

import com.echo.crypto.dto.CandlestickDto;
import com.echo.crypto.service.CandlestickServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/candles")
@RequiredArgsConstructor
public class CandlestickController {

    private final CandlestickServiceImpl candlestickService;

    @GetMapping
    public ResponseEntity<List<CandlestickDto>> getAll() {
        return ResponseEntity.ok(candlestickService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandlestickDto> getById(@PathVariable Long id) {
        CandlestickDto candlestickDto = candlestickService.findById(id);
        return ResponseEntity.ok(candlestickDto);
    }

    @PostMapping
    public ResponseEntity<CandlestickDto> create(@RequestBody CandlestickDto dto) {
        CandlestickDto createdCandlestickDto = candlestickService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandlestickDto);
    }

    @GetMapping("/load")
    public ResponseEntity<List<CandlestickDto>> loadCandles(
            @RequestParam String symbol,
            @RequestParam String interval,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(
                candlestickService.getCandlesticksForMarketDataset(symbol, interval, from, to)
        );
    }

}
