package com.expensetracker.budget.controller;

import com.expensetracker.budget.dto.BudgetResponse;
import com.expensetracker.budget.dto.CreateBudgetRequest;
import com.expensetracker.budget.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse createBudget(
            @Valid
            @RequestBody
            CreateBudgetRequest request
    ) {

        return budgetService.createBudget(
                request
        );
    }

    @GetMapping
    public List<BudgetResponse> getBudgets() {

        return budgetService.getBudgets();
    }

    @GetMapping("/{budgetId}")
    public BudgetResponse getBudgetById(
            @PathVariable UUID budgetId
    ) {

        return budgetService.getBudgetById(
                budgetId
        );
    }

    @DeleteMapping("/{budgetId}")
    public void deleteBudget(
            @PathVariable UUID budgetId
    ) {

        budgetService.deleteBudget(
                budgetId
        );
    }
}