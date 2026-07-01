package com.expensetracker.category.exception;

public class CategoryNotFoundException
        extends RuntimeException {

    public CategoryNotFoundException() {

        super("Category not found.");
    }
}