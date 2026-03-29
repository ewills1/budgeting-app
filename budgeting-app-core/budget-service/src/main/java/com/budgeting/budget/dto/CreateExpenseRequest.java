package com.budgeting.budget.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateExpenseRequest {
    private String category;
    private double amount;
    private String description;
    private LocalDate date;
}
