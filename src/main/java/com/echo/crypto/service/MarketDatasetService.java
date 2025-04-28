package com.echo.crypto.service;

import com.echo.crypto.dto.MarketDatasetDto;
import com.echo.crypto.entities.MarketDataset;
import com.echo.crypto.mapper.MarketDatasetMapper;
import com.echo.crypto.repository.MarketDatasetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketDatasetService {

    private final MarketDatasetRepository repository;
    private final MarketDatasetMapper mapper;

    public MarketDatasetService(MarketDatasetRepository repository, MarketDatasetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MarketDatasetDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public MarketDatasetDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));
    }

    public MarketDatasetDto create(MarketDatasetDto dto) {
        MarketDataset saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
