package com.budgeting.transaction.dto;

import com.budgeting.transaction.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateTransactionRequest {
    private String categoryId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String merchant;
    private LocalDate transactionDate;
}
