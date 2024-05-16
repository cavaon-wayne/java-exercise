package com.wayne.javaexercise.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public abstract class ExchangeDriver {

    protected HttpClient client;

    protected HttpClient getClient() {
        if(client == null) {
            client = HttpClient.newHttpClient();
        }
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public abstract double getRate(String fromCurrency, String toCurrency) throws IOException;

    public abstract List<String> getCurrencies() throws IOException;
}
