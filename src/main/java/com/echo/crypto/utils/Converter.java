package com.echo.crypto.utils;

public class Converter {

    public static String convertToBybitInterval(String interval) {
        switch (interval) {
            case "1h":
                return "60";
            case "3h":
                return "180";
            case "1d":
                return "1440";
            // Добавьте другие интервалы по мере необходимости
            default:
                return interval;
        }
    }
}
