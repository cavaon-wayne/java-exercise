package com.wayne.javaexercise.service;

import com.wayne.javaexercise.config.AppConfig;
import com.wayne.javaexercise.service.exchangerateapi.ExApiDriver;
import com.wayne.javaexercise.service.swopcx.SwopCxDriver;

import java.util.HashMap;
import java.util.Map;

public final class ExchangeManager {
    private static ExchangeManager instance;

    private final Map<String, ExchangeDriver> drivers;

    private ExchangeManager() {
        drivers=new HashMap<>();
    }

    public static ExchangeManager getInstance() {
        ExchangeManager result = instance;
        if (result != null) {
            return result;
        }

        synchronized(ExchangeManager.class) {
            if (instance == null) {
                instance = new ExchangeManager();
            }
            return instance;
        }
    }

    private void createSwopCxDriver() {
        this.putDriver("swop_cx",new SwopCxDriver());
    }

    private void createExchangeRateApiDriver() {
        this.putDriver("exchangerate-api", new ExApiDriver());
    }

    public ExchangeDriver getDriver() {
        return getDriver(AppConfig.exchangeProvider);
    }

    public void putDriver(String key, ExchangeDriver $driver){
        this.drivers.put(key, $driver);
    }

    public ExchangeDriver getDriver(String provider) {
        if(provider == null) {
            throw new IllegalArgumentException("provider cannot be null");
        }
        if (drivers.get(provider) == null) {
            switch (provider) {
                case "swop_cx":
                    createSwopCxDriver();
                    break;
                case "exchangerate-api":
                    createExchangeRateApiDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid provider: " + provider);
            }
        }
        return drivers.get(provider);
    }
}
