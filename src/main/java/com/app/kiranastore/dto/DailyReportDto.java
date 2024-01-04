package com.app.kiranastore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDto  {
    private BigDecimal startingBalance;
    private BigDecimal closingBalance;
    private List<ReportDto> reportDto;
}