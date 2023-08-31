package com.example.demo;

import com.example.demo.api.TransactionsApiDelegate;
import com.example.demo.entitys.TransactionEntity;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class delegate implements TransactionsApiDelegate {

    @Autowired
    private TransactionService transactionService;

    @Override
    public ResponseEntity<List<Transaction>> transactionsClientClientIdGet(String clientId) {
        List<TransactionEntity> entityTransactions = transactionService.getTransactionsByClientId(clientId);

        List<Transaction> apiModelTransactions = entityTransactions.stream()
                .map(transactionService::convertToApiModel)
                .collect(Collectors.toList());

        if (!apiModelTransactions.isEmpty()) {
            return ResponseEntity.ok(apiModelTransactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Transaction> transactionsPost(Transaction apiModelTransaction) {
        TransactionEntity entityTransaction = transactionService.convertToEntity(apiModelTransaction);

        TransactionEntity savedEntityTransaction = transactionService.saveTransaction(entityTransaction);

        Transaction savedApiModelTransaction = transactionService.convertToApiModel(savedEntityTransaction);

        if (savedApiModelTransaction != null) {
            return ResponseEntity.ok(savedApiModelTransaction);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
