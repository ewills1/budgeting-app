package com.budgeting.budget.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateExpenseRequest {
    private String category;
    private double amount;
    private String description;
    private LocalDate date;
}
