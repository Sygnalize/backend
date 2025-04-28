package com.echo.crypto.utils;

import com.echo.crypto.dto.BybitKlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class Http {

    private final WebClient.Builder webClientBuilder;

    public BybitKlineResponse fetchBybitKlineData(String fullUrl) {
        return webClientBuilder.build()
                .get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(BybitKlineResponse.class)
                .block(); // Можно заменить на async .subscribe() позже
    }
}
