package com.expensetracker.report.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface CategorySummaryProjection {

    UUID getCategoryId();

    String getCategoryName();

    BigDecimal getTotalSpent();
}