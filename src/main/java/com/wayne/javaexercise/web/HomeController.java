package com.wayne.javaexercise.web;

import com.wayne.javaexercise.config.AppConfig;
import com.wayne.javaexercise.service.ExchangeService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping()
    public String index(Model model) {
        meterRegistry.counter("page_viewed","page","home").increment();

        model.addAttribute("exchange_api_url", AppConfig.appUrl.replaceFirst("/$","") + "/api/v1/convert");
        model.addAttribute("currencies", ExchangeService.getInstance().getCurrencies());
        return "index";
    }
}
