package com.wayne.javaexercise.service;

import java.util.List;

public class CachedCurrencies {

    public CachedCurrencies(List<String> currencies, long timestamp) {
        this.currencies = currencies;
        this.timestamp = timestamp;
    }

    public List<String> currencies;

    public long timestamp;
}
