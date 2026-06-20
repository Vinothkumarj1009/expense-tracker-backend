package com.expensetracker.budget.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreateBudgetRequest {

    @NotNull
    private UUID categoryId;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;

    @NotNull
    private Integer year;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}