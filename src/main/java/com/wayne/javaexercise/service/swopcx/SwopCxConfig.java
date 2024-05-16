package com.wayne.javaexercise.service.swopcx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SwopCxConfig {
    public static String apiUrl;

    public static String apiKey;

    @Value("${service.swop_cx.api_url}")
    public void setApiUrl(String url) {
        apiUrl = url;
    }

    @Value("${service.swop_cx.api_key}")
    public void setApiKey(String key) {
        apiKey = key;
    }
}
