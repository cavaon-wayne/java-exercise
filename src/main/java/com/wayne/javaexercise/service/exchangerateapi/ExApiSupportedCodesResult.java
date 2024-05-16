package com.wayne.javaexercise.service.exchangerateapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class ExApiSupportedCodesResult {

    @JsonProperty("result")
    public String result;

    @JsonProperty("documentation")
    public String documentation;

    @JsonProperty("terms_of_use")
    public String termsOfUse;

    @JsonProperty("supported_codes")
    public List<List<String>> supportedCodes;

//    @JsonSetter("supported_codes")
//    public void setResult(List<List<String>> result) {
//        this.supportedCodes=result;
//    }

}
