package com.wayne.javaexercise.service.exchangerateapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayne.javaexercise.service.ExchangeDriver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ExApiDriver extends ExchangeDriver{

    private static final String rateURLTemplate = "/%s/pair/%s/%s";

    private static final String currenciesURLTemplate = "/%s/codes";

    public double getRate(String fromCurrency, String toCurrency) throws IOException  {
        if (ExApiConfig.baseUrl == null) {
            System.err.println("BASE_URL environment variable not set");
            return 0;
        }

        HttpClient client = this.getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ExApiConfig.baseUrl.replaceFirst("/$","")
                        + String.format(rateURLTemplate, ExApiConfig.apiKey, fromCurrency, toCurrency)))
                .build();


        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.thenApply(HttpResponse::body).join();

        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        ExApiExchangeRate rateResponse = objectMapper.readValue(jsonResponse, ExApiExchangeRate.class);

        return rateResponse.conversionRate;
    }

    public List<String> getCurrencies() throws IOException  {
        if (ExApiConfig.baseUrl == null) {
            System.err.println("BASE_URL environment variable not set");
            return null;
        }

        HttpClient client = this.getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ExApiConfig.baseUrl.replaceFirst("/$","")
                        + String.format(currenciesURLTemplate, ExApiConfig.apiKey)))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.thenApply(HttpResponse::body).join();

//        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();

        ExApiSupportedCodesResult rateResponse = objectMapper.readValue(jsonResponse, ExApiSupportedCodesResult.class);

        List<String> result=new ArrayList<>();

        for(List<String> currency:rateResponse.supportedCodes) {
            result.add(currency.get(0));
        }
//        System.out.println(rateResponse.supportedCodes.get(0).get(1));

        return result;
    }
}
