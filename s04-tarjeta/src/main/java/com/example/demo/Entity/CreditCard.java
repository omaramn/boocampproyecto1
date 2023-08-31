package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Builder
@Document(collection = "creditCards")
public class CreditCard {

    @Id
    private String id;

    private String clientId;
    private Double limit;
    private String cardNumber;
    private Double balance;
    private Double availableCredit;

    // Constructores, getters, setters, etc.

}