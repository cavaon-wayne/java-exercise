package com.wayne.javaexercise.api.v1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wayne.javaexercise.config.AppConfig;
import com.wayne.javaexercise.service.ExchangeDriver;
import com.wayne.javaexercise.service.ExchangeManager;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ConvertControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final double fixedRate=1.23456;

    @Test
    public void validRequestShouldReturnCorrectAmount() throws Exception {
        this.setUpServiceClient();

        mockMvc.perform(
                        post("/api/v1/convert").contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"amount\": 100, \"baseCurrency\": \"EUR\", \"targetCurrency\": \"USD\" }")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.amount").value(100 * fixedRate));
    }

    @Test
    public void invalidAmountShouldReturnErrors() throws Exception {
        this.setUpServiceClient();

        mockMvc.perform(
                        post("/api/v1/convert").contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"amount\": -100, \"baseCurrency\": \"EUR\", \"targetCurrency\": \"USD\" }")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //.andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.amount").value("must be greater than or equal to 0"));
    }

    @Test
    public void notSupportedCurrencyShouldReturnErrors() throws Exception {
        this.setUpServiceClient();

        mockMvc.perform(
                        post("/api/v1/convert").contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"amount\": 100, \"baseCurrency\": \"NZD\", \"targetCurrency\": \"USD\" }")
                                .accept(MediaType.APPLICATION_JSON)
                )
                //.andDo(print())
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.baseCurrency").value("must be a valid currency code"));
    }

    private void setUpServiceClient(){

        //This will be working when we don't want to mock the client
        ExchangeDriver driver=mock(ExchangeDriver.class);

        AppConfig config=new AppConfig();
        config.setExchangeProvider("testing");
        config.setExchangeCacheTTL(-1);

        ExchangeManager.getInstance().putDriver("testing", driver);

        List<String> currencies=new ArrayList<>();
        currencies.add("USD");
        currencies.add("EUR");
        currencies.add("AUD");

        try {
            when(driver.getCurrencies()).thenReturn(currencies);
            when(driver.getRate(anyString(), anyString())).thenReturn(fixedRate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}