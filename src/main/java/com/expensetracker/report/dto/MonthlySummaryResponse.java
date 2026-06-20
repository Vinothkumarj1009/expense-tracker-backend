package com.expensetracker.report.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MonthlySummaryResponse {

    private Integer month;

    private Integer year;

    private BigDecimal totalSpent;
}