package com.app.kiranastore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportDto extends TransactionDto{
    private BigDecimal balance;
}
