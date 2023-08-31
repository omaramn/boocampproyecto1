package com.example.demo.service;

import com.example.demo.entitys.TransactionEntity;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
@Service
public class TransactionService  {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionEntity saveTransaction(TransactionEntity transaction) {
        return transactionRepository.save(transaction).block();
    }

    public List<TransactionEntity> getTransactionsByClientId(String clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    public Transaction convertToApiModel(TransactionEntity entity) {
        if (entity == null) return null;

        Transaction apiModel = new Transaction();
        apiModel.setId(entity.getId());
        apiModel.setClientId(entity.getClientId());
        apiModel.setDescription(entity.getDescription());
        apiModel.setAmount(entity.getAmount());
        apiModel.setTransactionDate(entity.getTransactionDate().atOffset(OffsetDateTime.now().getOffset()));

        // Aquí usamos TransactionTypeEnum
        apiModel.setTransactionType(Transaction.TransactionTypeEnum.valueOf(entity.getTransactionType().name()));

        return apiModel;
    }

    public TransactionEntity convertToEntity(Transaction apiModel) {
        if (apiModel == null) return null;

        TransactionEntity entity = new TransactionEntity();
        entity.setId(apiModel.getId());
        entity.setClientId(apiModel.getClientId());
        entity.setDescription(apiModel.getDescription());
        entity.setAmount(apiModel.getAmount());
        entity.setTransactionDate(apiModel.getTransactionDate().toLocalDateTime());
        entity.setTransactionType(TransactionEntity.TransactionType.valueOf(apiModel.getTransactionType().name()));

        return entity;
    }

}
