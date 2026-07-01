package com.expensetracker.category.exception;

public class CategoryInUseException
        extends RuntimeException {

    public CategoryInUseException() {

        super("Category is being used by expenses or budgets.");
    }
}