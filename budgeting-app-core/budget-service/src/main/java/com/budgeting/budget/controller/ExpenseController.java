package com.budgeting.budget.controller;

import com.budgeting.budget.dto.CreateExpenseRequest;
import com.budgeting.budget.dto.ExpenseResponse;
import com.budgeting.budget.dto.UpdateExpenseRequest;
import com.budgeting.budget.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets/{budgetId}/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String budgetId,
            @RequestBody CreateExpenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseService.createExpense(userDetails.getUsername(), budgetId, request));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String budgetId) {
        return ResponseEntity.ok(expenseService.getExpensesForBudget(userDetails.getUsername(), budgetId));
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String budgetId,
            @PathVariable String expenseId) {
        return ResponseEntity.ok(expenseService.getExpenseById(userDetails.getUsername(), budgetId, expenseId));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String budgetId,
            @PathVariable String expenseId,
            @RequestBody UpdateExpenseRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(userDetails.getUsername(), budgetId, expenseId, request));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String budgetId,
            @PathVariable String expenseId) {
        expenseService.deleteExpense(userDetails.getUsername(), budgetId, expenseId);
        return ResponseEntity.noContent().build();
    }
}
