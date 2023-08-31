package com.example.demo.DTOS;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private String clientId;
    private String referenceId; // Esto puede ser el número de tarjeta, ID de cuenta, etc.
    private Double amount;
    private String description;

    private OffsetDateTime transactionDate;
    private TransactionType transactionType;

    public enum TransactionType {
        CREDIT_CARD,
        SAVINGS_ACCOUNT,
        LOAN
        // ... otros tipos según sea necesario.
    }
}