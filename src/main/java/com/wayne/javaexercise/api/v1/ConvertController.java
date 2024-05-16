package com.wayne.javaexercise.api.v1;

import com.wayne.javaexercise.JsonResult;
import com.wayne.javaexercise.service.ExchangeService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConvertController extends AbstractRestController {

    @Autowired
    private MeterRegistry meterRegistry;

    @PostMapping("/convert")
    @ResponseBody
    public ResponseEntity<JsonResult> convert(@Valid @RequestBody ConvertForm form) {
        meterRegistry.counter("convert_api_called","base_currency",form.getBaseCurrency(),"target_currency",form.getTargetCurrency()).increment();

        JsonResult result = new JsonResult(true, "success");

        try {
            result.setData(new ConvertResult(
                    ExchangeService.getInstance().setMeterRegistry(meterRegistry).getValue(
                            form.getAmount(),
                            form.getBaseCurrency(),
                            form.getTargetCurrency()
                    )
            ));
        } catch (Exception e) {
            e.printStackTrace();

            result.setSuccess(false);
            result.setMessage("Something went wrong, please try again later.");
        }


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
