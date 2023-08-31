package com.example.creditservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Credit")
public class CreditEntity {

    @Id
    private String id;
    private creditType type;
    private String id_cliente;
    private Integer amount;
    public enum creditType{
        PERSONAL,EMPRESARIAL
    }
}
