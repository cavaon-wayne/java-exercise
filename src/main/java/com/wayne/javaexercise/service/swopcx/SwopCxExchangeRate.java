package com.wayne.javaexercise.service.swopcx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SwopCxExchangeRate {

    @JsonProperty("base_currency")
    public String baseCurrency;

    @JsonProperty("quote_currency")
    public String quoteCurrency;

    @JsonProperty("quote")
    public double quote;

    @JsonProperty("date")
    public String date;

}
