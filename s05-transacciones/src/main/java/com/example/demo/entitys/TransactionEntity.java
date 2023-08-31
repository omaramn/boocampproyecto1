package com.example.demo.entitys;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    private String id;
    private String clientId;
    private String referenceId;  // Número de tarjeta, número de cuenta, ID de préstamo, etc.
    private Double amount;
    private String description;

    private LocalDateTime transactionDate;
    private TransactionType transactionType;

    public enum TransactionType {
        CREDIT_CARD,
        SAVINGS_ACCOUNT,
        LOAN
        // ... otros tipos según sea necesario.
    }
}
