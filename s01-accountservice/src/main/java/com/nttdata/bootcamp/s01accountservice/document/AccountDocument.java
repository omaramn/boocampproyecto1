package com.nttdata.bootcamp.s01accountservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "accounts")
public class AccountDocument {

    @Id
    private String id;

    private AccountDetails.TypeEnum type;

    private List<String> ownerClients;

    private List<String> signClients;

    private BigDecimal balance;
    
}