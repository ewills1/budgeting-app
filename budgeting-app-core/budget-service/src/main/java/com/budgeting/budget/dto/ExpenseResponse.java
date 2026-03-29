package com.budgeting.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private String id;
    private String budgetId;
    private String userId;
    private String category;
    private double amount;
    private String description;
    private LocalDate date;
    private Instant createdAt;
}
