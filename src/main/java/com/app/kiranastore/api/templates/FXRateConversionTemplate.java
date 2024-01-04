package com.app.kiranastore.api.templates;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class FXRateConversionTemplate {
    private boolean success;
    private BigDecimal result;
    private boolean historical;
}