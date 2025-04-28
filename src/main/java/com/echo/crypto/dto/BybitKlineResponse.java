package com.echo.crypto.dto;

import java.util.List;
import java.util.Map;

// В каком виде данные приходят с байбита

public record BybitKlineResponse(
        int retCode,
        String retMsg,
        BybitResult result,
        Map<String, Object> retExtInfo,
        long time
) {
    public record BybitResult(
            String category,
            String symbol,
            List<List<String>> list // ["timestamp", "open", "high", "low", "close", "volume", "turnover"]
    ) {}
}
