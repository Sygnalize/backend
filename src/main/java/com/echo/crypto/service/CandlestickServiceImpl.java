package com.echo.crypto.service;

import com.echo.crypto.dto.BybitKlineResponse;
import com.echo.crypto.dto.CandlestickDto;
import com.echo.crypto.entities.Candlestick;
import com.echo.crypto.entities.MarketDataset;
import com.echo.crypto.mapper.CandlestickMapper;
import com.echo.crypto.repository.CandlestickRepository;
import com.echo.crypto.repository.MarketDatasetRepository;
import com.echo.crypto.utils.Converter;
import com.echo.crypto.utils.Http;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandlestickServiceImpl implements CandlestickService {

    private final CandlestickRepository candlestickRepository;
    private final MarketDatasetRepository marketDatasetRepository;  // Репозиторий для MarketDataset
    private final Http httpUtil;


    public List<CandlestickDto> findAll() {
        return candlestickRepository.findAll().stream()
                .map(CandlestickMapper::toDto)
                .toList();
    }

    public CandlestickDto findById(Long id) {
        return candlestickRepository.findById(id)
                .map(CandlestickMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candlestick not found"));
    }

    public CandlestickDto save(CandlestickDto dto) {
        Candlestick entity = CandlestickMapper.toEntity(dto);
        Candlestick saved = candlestickRepository.save(entity);
        return CandlestickMapper.toDto(saved);
    }

    @Override
    public List<CandlestickDto> getCandlesticksForMarketDataset(
            String symbol,
            String interval,
            LocalDateTime from,
            LocalDateTime to
    ) {

        if (from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }
        Optional<MarketDataset> existingDataset = marketDatasetRepository
                .findBySymbolAndFromDateAndToDate(symbol, from.toLocalDate(), to.toLocalDate());

        if (existingDataset.isPresent()) {
            System.out.println("Датасет с такими датами уже есть, иду в базу");
            return existingDataset.get().getCandles().stream()
                    .map(CandlestickMapper::toDto)
                    .toList();
        }

        String url = UriComponentsBuilder.fromHttpUrl("https://api.bybit.com/v5/market/kline")
                .queryParam("category", "spot")
                .queryParam("symbol", symbol)
                .queryParam("interval", Converter.convertToBybitInterval(interval))
                .queryParam("start", from.toInstant(ZoneOffset.UTC).toEpochMilli())
                .queryParam("end", to.toInstant(ZoneOffset.UTC).toEpochMilli())
                .toUriString();

        BybitKlineResponse response = httpUtil.fetchBybitKlineData(url);

        if (response == null || response.result() == null || response.result().list() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bybit API returned invalid response");
        }

        MarketDataset dataset = new MarketDataset();
        dataset.setSymbol(symbol);
        dataset.setFromDate(from.toLocalDate());
        dataset.setToDate(to.toLocalDate());

        List<Candlestick> candles = response.result().list().stream()
                .map(arr -> new Candlestick(
                        null,
                        Long.parseLong(arr.get(0)),
                        new BigDecimal(arr.get(1)),
                        new BigDecimal(arr.get(2)),
                        new BigDecimal(arr.get(3)),
                        new BigDecimal(arr.get(4)),
                        new BigDecimal(arr.get(5)),
                        dataset
                ))
                .toList();

        dataset.setCandles(candles); // Привязка свечей к датасету (Bi-directional)

        // Сохраняем датасет и каскадно свечи
        marketDatasetRepository.save(dataset);

        return candles.stream()
                .map(CandlestickMapper::toDto)
                .toList();
    }

    @Override
    public List<CandlestickDto> getCandlesticksForBacktest(Long backtestId) {
        return List.of();
    }

    @Override
    public void deleteOutliersForDataset(Long datasetId) {

    }

    @Override
    public List<CandlestickDto> normalizeCandlesticks(Long datasetId) {
        return List.of();
    }

    @Override
    public Map<String, BigDecimal> getCandlestickSummary(Long datasetId) {
        return Map.of();
    }

    private BigDecimal averageClose(List<Candlestick> candles) { /* ... */ return BigDecimal.ONE; }
    private BigDecimal standardDeviationClose(List<Candlestick> candles, BigDecimal avg) { /* ... */ return BigDecimal.ONE; }
    private BigDecimal computeVolumeThreshold(List<Candlestick> candles) { /* ... */ return BigDecimal.TEN; }
}
