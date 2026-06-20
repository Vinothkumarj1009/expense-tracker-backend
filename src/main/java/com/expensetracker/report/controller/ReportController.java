package com.expensetracker.report.controller;

import com.expensetracker.report.dto.BudgetComparisonResponse;
import com.expensetracker.report.dto.CategorySummaryResponse;
import com.expensetracker.report.dto.MonthlySummaryResponse;
import com.expensetracker.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly-summary")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        return reportService.getMonthlySummary(
                month,
                year
        );
    }

    @GetMapping("/category-summary")
    public List<CategorySummaryResponse> getCategorySummary(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        return reportService.getCategorySummary(
                month,
                year
        );
    }

    @GetMapping("/budget-comparison")
    public List<BudgetComparisonResponse>
    getBudgetComparison(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        return reportService
                .getBudgetComparison(
                        month,
                        year
                );
    }
}