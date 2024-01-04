package com.app.kiranastore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StoreDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private BigDecimal balance;
}
