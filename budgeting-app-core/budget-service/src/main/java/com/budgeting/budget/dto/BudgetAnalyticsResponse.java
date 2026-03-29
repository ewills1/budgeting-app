package com.budgeting.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetAnalyticsResponse {
    private String budgetId;
    private double monthlyIncome;
    private double totalExpenses;
    private double remainingBudget;
    private Map<String, Double> spentByCategory;
    private Map<String, Double> remainingByCategory;
    private Map<String, Double> percentUsedByCategory;
}