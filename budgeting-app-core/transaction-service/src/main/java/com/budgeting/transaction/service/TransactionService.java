package com.budgeting.transaction.service;

import com.budgeting.transaction.dto.CreateTransactionRequest;
import com.budgeting.transaction.dto.TransactionResponse;
import com.budgeting.transaction.dto.UpdateTransactionRequest;
import com.budgeting.transaction.exception.TransactionNotFoundException;
import com.budgeting.transaction.model.Transaction;
import com.budgeting.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionResponse createTransaction(String userId, CreateTransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .userId(userId)
                .categoryId(request.getCategoryId())
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .merchant(request.getMerchant())
                .transactionDate(request.getTransactionDate())
                .build();

        return toResponse(transactionRepository.save(transaction));
    }

    public List<TransactionResponse> getTransactionsForUser(String userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(String userId, String transactionId) {
        Transaction transaction = findTransactionForUser(userId, transactionId);
        return toResponse(transaction);
    }

    public TransactionResponse updateTransaction(String userId, String transactionId, UpdateTransactionRequest request) {
        Transaction transaction = findTransactionForUser(userId, transactionId);

        transaction.setCategoryId(request.getCategoryId());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setMerchant(request.getMerchant());
        transaction.setTransactionDate(request.getTransactionDate());

        return toResponse(transactionRepository.save(transaction));
    }

    public void deleteTransaction(String userId, String transactionId) {
        Transaction transaction = findTransactionForUser(userId, transactionId);
        transactionRepository.delete(transaction);
    }

    private Transaction findTransactionForUser(String userId, String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found: " + transactionId));
        if (!transaction.getUserId().equals(userId)) {
            throw new TransactionNotFoundException("Transaction not found: " + transactionId);
        }
        return transaction;
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .categoryId(transaction.getCategoryId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .merchant(transaction.getMerchant())
                .transactionDate(transaction.getTransactionDate())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}
