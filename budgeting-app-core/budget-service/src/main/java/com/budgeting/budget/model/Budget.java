package com.budgeting.budget.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.Instant;
import java.util.Map;

@Document(collection = "budgets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    @Id
    private String id;

    @Indexed
    private String userId;

    private String name;

    private double monthlyIncome;

    private Map<String, Double> categoryLimits;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}