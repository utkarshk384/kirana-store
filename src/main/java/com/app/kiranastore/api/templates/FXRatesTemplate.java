package com.app.kiranastore.api.templates;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
public class FXRatesTemplate {
    private boolean success;
    private String base;
    private LocalDateTime date;
    private HashMap<String, BigDecimal> rates;
}