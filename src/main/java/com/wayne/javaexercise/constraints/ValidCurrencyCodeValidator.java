package com.wayne.javaexercise.constraints;

import com.wayne.javaexercise.service.ExchangeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    private Boolean isOptional;

    @Override
    public void initialize(ValidCurrencyCode validCurrencyCode) {
        this.isOptional = validCurrencyCode.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsIsoCode = false;

        List<String> result= ExchangeService.getInstance().getCurrencies();
        if(result == null) {
            Set<Currency> currencies = Currency.getAvailableCurrencies();
            try {
                containsIsoCode = currencies.contains(Currency.getInstance(value));
            } catch (IllegalArgumentException e) {
            }
        }else{
            containsIsoCode = result.contains(value);
        }

        return isOptional ? (containsIsoCode || value==null || value.isEmpty() ) : containsIsoCode;
    }
}
