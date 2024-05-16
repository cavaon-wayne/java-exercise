package com.wayne.javaexercise.service.exchangerateapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExApiExchangeRate {

    @JsonProperty("result")
    public String result;

    @JsonProperty("documentation")
    public String documentation;

    @JsonProperty("terms_of_use")
    public String termsOfUse;

    @JsonProperty("time_last_update_unix")
    public int timeLastUpdateUnix;

    @JsonProperty("time_last_update_utc")
    public String timeLastUpdateUtc;

    @JsonProperty("time_next_update_unix")
    public int timeNextUpdateUnix;
    
    @JsonProperty("time_next_update_utc")
    public String time_next_update_utc;

    @JsonProperty("base_code")
    public String baseCode;

    @JsonProperty("target_code")
    public String targetCode;

    @JsonProperty("conversion_rate")
    public double conversionRate;
}
