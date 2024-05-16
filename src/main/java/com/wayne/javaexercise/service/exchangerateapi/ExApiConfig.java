package com.wayne.javaexercise.service.exchangerateapi;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExApiConfig {
    public static String baseUrl;

    public static String apiKey;

    @Value("${service.exchangerate_api.base_url}")
    public void setBaseUrl(String url) {
        baseUrl = url;
    }

    @Value("${service.exchangerate_api.api_key}")
    public void setApiKey(String key) {
        apiKey = key;
    }
}
