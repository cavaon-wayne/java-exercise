package com.wayne.javaexercise.service;

import com.wayne.javaexercise.config.AppConfig;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ExchangeService {
    private static ExchangeService instance;

    public static ExchangeService getInstance() {
        ExchangeService result = instance;
        if (result != null) {
            return result;
        }

        synchronized(ExchangeService.class) {
            if (instance == null) {
                instance = new ExchangeService();

//                instance.setMeterRegistry(ApplicationContextProvider.getApplicationContext().getBean(MeterRegistry.class));
            }
            return instance;
        }
    }

    private final HashMap<String, CachedRate> cacheRates = new HashMap<>();

    private final HashMap<String, CachedCurrencies> cacheCurrencies = new HashMap<>();

    private MeterRegistry meterRegistry;

    public ExchangeService setMeterRegistry(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        return this;
    }

    public double getValue(double value, String baseCurrency, String targetCurrency){
        return value * this.getRate(baseCurrency, targetCurrency);
    }

    public double getRate(String baseCurrency, String targetCurrency) {
        if(baseCurrency.equals(targetCurrency)) return 1;

        System.out.println(meterRegistry==null?"testing":"no null testing");

        String key=baseCurrency+"-"+targetCurrency;

        CachedRate cachedRate = cacheRates.get(key);

        long timestamp = Instant.now().getEpochSecond();

        if (cachedRate == null || cachedRate.timestamp + AppConfig.exchangeCacheTtl < timestamp) {
            double rate=0;
            try {
                Optional.ofNullable(meterRegistry).ifPresent(mr -> mr.counter("exchange_api_hit","pair_name", key).increment());
                //"EUR", "USD"
                rate = ExchangeManager.getInstance().getDriver().getRate(baseCurrency,targetCurrency);

            }catch (Exception e) {
                e.printStackTrace();
            }

            cachedRate = new CachedRate(rate, Instant.now().getEpochSecond());

            cacheRates.put(key, cachedRate);
        } else {
            Optional.ofNullable(meterRegistry).ifPresent(mr -> mr.counter("exchange_cache_hit","pair_name", key).increment());

            System.out.println("Retrieved rate '" + key + "' from cache.");
        }
        return cachedRate.rate;
    }
    public List<String> getCurrencies() {
        String key=AppConfig.exchangeProvider;

        CachedCurrencies cachedCurrencies = cacheCurrencies.get(key);

        long timestamp = Instant.now().getEpochSecond();

        List<String> currencies = null;
        if (cachedCurrencies == null || cachedCurrencies.timestamp + AppConfig.exchangeCacheTtl < timestamp) {

            try{
                Optional.ofNullable(meterRegistry).ifPresent(mr -> mr.counter("currencies_api_called","provider", key).increment());

                currencies = ExchangeManager.getInstance().getDriver().getCurrencies();
            }catch (Exception e) {
                e.printStackTrace();
            }

            cachedCurrencies = new CachedCurrencies(currencies, Instant.now().getEpochSecond());

            cacheCurrencies.put(key, cachedCurrencies);
        } else {
            Optional.ofNullable(meterRegistry).ifPresent(mr -> mr.counter("currencies_cache_hit","provider", key).increment());

            System.out.println("Retrieved currencies '" + key + "' from cache.");
        }

        return cachedCurrencies.currencies;
    }

}
