package com.wayne.javaexercise.service.swopcx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SwopCxCurrency {

    @JsonProperty("code")
    public String code;

    @JsonProperty("numeric_code")
    public String numericCode;

    @JsonProperty("decimal_digits")
    public int decimalDigits;

    @JsonProperty("name")
    public String name;

    @JsonProperty("active")
    public Boolean active;
}
