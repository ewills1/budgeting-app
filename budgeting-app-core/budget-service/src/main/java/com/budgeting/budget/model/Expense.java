package com.budgeting.budget.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.Instant;
import java.time.LocalDate;
@Document(collection = "expenses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    private String id;
    @Indexed
    private String budgetId;
    @Indexed
    private String userId;
    private String category;
    private double amount;
    private String description;
    private LocalDate date;
    @CreatedDate
    private Instant createdAt;
}