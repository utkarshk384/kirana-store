package com.app.kiranastore.dto;

import com.app.kiranastore.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {
    private Long id;
    private BigDecimal amount;
    private String transactionUID;
    private Long storeId;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
}
