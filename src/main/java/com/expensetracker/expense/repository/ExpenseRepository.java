package com.expensetracker.expense.repository;

import com.expensetracker.expense.entity.Expense;
import com.expensetracker.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import com.expensetracker.report.projection.CategorySummaryProjection;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository
        extends JpaRepository<Expense, UUID> {

    @EntityGraph(attributePaths = {"category"})
    Page<Expense> findByUser(
            User user,
            Pageable pageable
    );

    Optional<Expense> findByIdAndUser(
            UUID id,
            User user
    );

    @Query("""
    select coalesce(sum(e.amount), 0)
    from Expense e
    where e.user = :user
    and month(e.createdAt) = :month
    and year(e.createdAt) = :year
""")
    BigDecimal getMonthlyTotal(
            User user,
            Integer month,
            Integer year
    );

    @Query("""
    select
        c.id as categoryId,
        c.name as categoryName,
        coalesce(sum(e.amount),0) as totalSpent
    from Expense e
    join e.category c
    where e.user = :user
      and month(e.createdAt) = :month
      and year(e.createdAt) = :year
    group by c.id, c.name
    order by totalSpent desc
""")
    List<CategorySummaryProjection> getCategorySummary(
            User user,
            Integer month,
            Integer year
    );

    @Query("""
    select coalesce(sum(e.amount),0)
    from Expense e
    where e.user = :user
      and e.category.id = :categoryId
      and month(e.createdAt) = :month
      and year(e.createdAt) = :year
""")
    java.math.BigDecimal getSpentAmount(
            User user,
            UUID categoryId,
            Integer month,
            Integer year
    );

    List<Expense> findTop5ByUserOrderByCreatedAtDesc(
            User user
    );

    @Query("""
       SELECT e.category.id,
              e.category.name,
              SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
       GROUP BY e.category.id, e.category.name
       ORDER BY SUM(e.amount) DESC
       """)
    List<Object[]> getCategorySpending(
            @Param("user") User user
    );

    List<Expense> findByUser(User user);

    @Query("""
       SELECT
           MONTH(e.createdAt),
           SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
           AND YEAR(e.createdAt) = :year
       GROUP BY MONTH(e.createdAt)
       ORDER BY MONTH(e.createdAt)
       """)
    List<Object[]> getMonthlyTrend(
            User user,
            Integer year
    );

    @Query("""
       SELECT
           c.id,
           c.name,
           SUM(e.amount)
       FROM Expense e
       JOIN e.category c
       WHERE e.user = :user
       GROUP BY c.id, c.name
       ORDER BY SUM(e.amount) DESC
       """)
    List<Object[]> getTopCategories(
            User user
    );

    List<Expense> findByUserOrderByAmountDesc(
            User user,
            org.springframework.data.domain.Pageable pageable
    );

    @Query("""
       SELECT
           MONTH(e.createdAt),
           SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
           AND e.category.id = :categoryId
           AND YEAR(e.createdAt) = :year
       GROUP BY MONTH(e.createdAt)
       ORDER BY MONTH(e.createdAt)
       """)
    List<Object[]> getCategoryTrend(
            User user,
            UUID categoryId,
            Integer year
    );

    @Query("""
       SELECT
           e.category.id,
           SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
           AND MONTH(e.createdAt) = :month
           AND YEAR(e.createdAt) = :year
       GROUP BY e.category.id
       """)
    List<Object[]> getCategorySpendingForMonth(
            User user,
            Integer month,
            Integer year
    );
}