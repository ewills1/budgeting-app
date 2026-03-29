package com.budgeting.budget.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateBudgetRequest {
    private String name;
    private double monthlyIncome;
    private Map<String, Double> categoryLimits;
}
