package com.app.kiranastore.api;

import com.app.kiranastore.api.templates.FXRatesTemplate;
import com.app.kiranastore.exception.BadRequestExcepetion;
import com.app.kiranastore.exception.InternalServerException;
import com.app.kiranastore.api.templates.FXRateConversionTemplate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@NoArgsConstructor
@Service
public class FXRate {
    public BigDecimal INRtoUSD(BigDecimal amount){
        String uri = String.format("https://api.fxratesapi.com/convert?from=USD&to=INR&amount=%f&format=json", amount);
        RestTemplate restTemplate = new RestTemplate();
        FXRateConversionTemplate res = restTemplate.getForObject(uri, FXRateConversionTemplate.class);

        if(!res.isSuccess())
            throw new InternalServerException("Unable to convert currency");

        return res.getResult();
    }

    public BigDecimal getCurrencyPrice(String currency){
        String uri = "https://api.fxratesapi.com/latest?base=INR";
        RestTemplate restTemplate = new RestTemplate();
        FXRatesTemplate res = restTemplate.getForObject(uri, FXRatesTemplate.class);

        if(!res.isSuccess())
            throw new InternalServerException("Unable to fetch currency prices");

        BigDecimal conversionRate = res.getRates().get(currency);

        if(conversionRate == null)
            throw new BadRequestExcepetion("Invalid currency is passed.");

        return conversionRate;
    }

}
