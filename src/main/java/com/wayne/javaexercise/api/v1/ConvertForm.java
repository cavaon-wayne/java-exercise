package com.wayne.javaexercise.api.v1;

import com.wayne.javaexercise.constraints.ValidCurrencyCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ConvertForm {

    @NotNull
    @Max(value=Long.MAX_VALUE)
    @Min(value=0)
    private double amount;

    @NotNull
    @Size(min=3, max=3, message="length must be 3")
    @ValidCurrencyCode
    private String baseCurrency;


    @NotNull
    @Size(min=3, max=3, message="length must be 3")
    @ValidCurrencyCode
    private String targetCurrency;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
