package com.wayne.javaexercise.service.swopcx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayne.javaexercise.service.ExchangeDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class SwopCxDriver extends ExchangeDriver {

    private static final String rateURLTemplate = "/rates/%s/%s";

    private static final String currenciesURLTemplate = "/currencies";

    public double getRate(String fromCurrency, String toCurrency) throws IOException  {
        if (SwopCxConfig.apiUrl == null) {
            System.err.println("API_URL environment variable not set");
            return 0;
        }

        HttpClient client = this.getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SwopCxConfig.apiUrl.replaceFirst("/$","") + String.format(rateURLTemplate,fromCurrency,toCurrency)))
                .header("Authorization", "ApiKey " + SwopCxConfig.apiKey)
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.thenApply(HttpResponse::body).join();

        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        SwopCxExchangeRate rateResponse = objectMapper.readValue(jsonResponse, SwopCxExchangeRate.class);

        return rateResponse.quote;
    }

    public List<String> getCurrencies() throws IOException  {
        if (SwopCxConfig.apiUrl == null) {
            System.err.println("API_URL environment variable not set");
            return null;
        }

        HttpClient client = this.getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SwopCxConfig.apiUrl.replaceFirst("/$","") + currenciesURLTemplate))
                .header("Authorization", "ApiKey " + SwopCxConfig.apiKey)
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.thenApply(HttpResponse::body).join();

//        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        List<SwopCxCurrency> rateResponse = objectMapper.readValue(jsonResponse,objectMapper.getTypeFactory().constructCollectionType(List.class, SwopCxCurrency.class));

        List<String> result=new ArrayList<>();

        for(SwopCxCurrency currency:rateResponse) {
            if(currency.active) {
                result.add(currency.code);
            }
        }

        return result;
    }
}
