package com.wayne.javaexercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    public static String appUrl;
    public static String exchangeProvider;

    public static int exchangeCacheTtl; //seconds


    @Value("${app.url}")
    public void setAppUrl(String value) {
        appUrl = value;
    }

    @Value("${exchange.provider}")
    public void setExchangeProvider(String value) {
        exchangeProvider = value;
    }

    @Value("${exchange.cache_ttl}")
    public void setExchangeCacheTTL(int value) {
        exchangeCacheTtl = value;
    }
}
