package com.budgeting.transaction.dto;

import com.budgeting.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private String userId;
    private String categoryId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String merchant;
    private LocalDate transactionDate;
    private Instant createdAt;
    private Instant updatedAt;
}
