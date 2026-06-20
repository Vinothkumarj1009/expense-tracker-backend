package com.expensetracker.budget.repository;

import com.expensetracker.budget.entity.Budget;
import com.expensetracker.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository
        extends JpaRepository<Budget, UUID> {

    List<Budget> findByUser(User user);

    Optional<Budget> findByIdAndUser(
            UUID id,
            User user
    );

    List<Budget> findByUserAndMonthAndYear(
            User user,
            Integer month,
            Integer year
    );

}