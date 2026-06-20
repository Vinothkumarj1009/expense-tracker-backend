package com.expensetracker.report.service;

import com.expensetracker.budget.repository.BudgetRepository;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.report.dto.BudgetComparisonResponse;
import com.expensetracker.report.dto.CategorySummaryResponse;
import com.expensetracker.report.dto.MonthlySummaryResponse;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExpenseRepository expenseRepository;

    private final UserService userService;

    private final BudgetRepository budgetRepository;

    public MonthlySummaryResponse getMonthlySummary(
            Integer month,
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        BigDecimal totalSpent =
                expenseRepository.getMonthlyTotal(
                        user,
                        month,
                        year
                );

        return MonthlySummaryResponse.builder()
                .month(month)
                .year(year)
                .totalSpent(totalSpent)
                .build();
    }

    public List<CategorySummaryResponse> getCategorySummary(
            Integer month,
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .getCategorySummary(
                        user,
                        month,
                        year
                )
                .stream()
                .map(summary ->
                        CategorySummaryResponse.builder()
                                .categoryId(
                                        summary.getCategoryId()
                                )
                                .categoryName(
                                        summary.getCategoryName()
                                )
                                .totalSpent(
                                        summary.getTotalSpent()
                                )
                                .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BudgetComparisonResponse> getBudgetComparison(
            Integer month,
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        return budgetRepository
                .findByUserAndMonthAndYear(
                        user,
                        month,
                        year
                )
                .stream()
                .map(budget -> {

                    java.math.BigDecimal spentAmount =
                            expenseRepository.getSpentAmount(
                                    user,
                                    budget.getCategory().getId(),
                                    month,
                                    year
                            );

                    java.math.BigDecimal remaining =
                            budget.getAmount()
                                    .subtract(spentAmount);

                    return BudgetComparisonResponse
                            .builder()
                            .categoryId(
                                    budget.getCategory().getId()
                            )
                            .categoryName(
                                    budget.getCategory().getName()
                            )
                            .budgetAmount(
                                    budget.getAmount()
                            )
                            .spentAmount(
                                    spentAmount
                            )
                            .remainingAmount(
                                    remaining
                            )
                            .build();
                })
                .toList();
    }
}