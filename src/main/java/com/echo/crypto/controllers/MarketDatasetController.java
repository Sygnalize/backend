package com.echo.crypto.controllers;

import com.echo.crypto.dto.MarketDatasetDto;
import com.echo.crypto.dto.MarketDatasetShortDto;
import com.echo.crypto.service.MarketDatasetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasets")
public class MarketDatasetController {

    private final MarketDatasetService marketDatasetService;

    public MarketDatasetController(MarketDatasetService marketDatasetService) {
        this.marketDatasetService = marketDatasetService;
    }

    @GetMapping
    public List<MarketDatasetDto> getAll() {
        return marketDatasetService.findAll();
    }

    @GetMapping("/{id}")
    public MarketDatasetDto getById(@PathVariable Long id) {
        return marketDatasetService.findById(id);
    }

    @PostMapping
    public MarketDatasetDto create(@RequestBody MarketDatasetDto dto) {
        return marketDatasetService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        marketDatasetService.delete(id);
    }

//    @GetMapping("/short")
//    public List<MarketDatasetShortDto> getAllShort() {
//        return marketDatasetService.findAllShort();
//    }

}
