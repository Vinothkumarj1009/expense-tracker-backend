package com.expensetracker.expense.controller;

import com.expensetracker.expense.dto.CreateExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.dto.UpdateExpenseRequest;
import com.expensetracker.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ExpenseResponse createExpense(
            @Valid @RequestBody CreateExpenseRequest request
    ) {

        return expenseService.createExpense(request);
    }

    @GetMapping
    public Page<ExpenseResponse> getExpenses(

            @RequestParam(
                    defaultValue = "0"
            ) int page,

            @RequestParam(
                    defaultValue = "10"
            ) int size
    ) {

        return expenseService.getExpenses(
                page,
                size
        );
    }

    @GetMapping("/{expenseId}")
    public ExpenseResponse getExpenseById(
            @PathVariable UUID expenseId
    ) {

        return expenseService.getExpenseById(
                expenseId
        );
    }

    @PutMapping("/{expenseId}")
    public ExpenseResponse updateExpense(

            @PathVariable UUID expenseId,

            @Valid
            @RequestBody
            UpdateExpenseRequest request
    ) {

        return expenseService.updateExpense(
                expenseId,
                request
        );
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(
            @PathVariable UUID expenseId
    ) {

        expenseService.deleteExpense(
                expenseId
        );
    }
}