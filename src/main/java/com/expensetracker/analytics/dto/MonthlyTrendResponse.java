package com.expensetracker.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MonthlyTrendResponse {

    private Integer month;

    private BigDecimal totalAmount;
}