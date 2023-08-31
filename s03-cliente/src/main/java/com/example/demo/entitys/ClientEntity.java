package com.example.demo.entitys;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "clients")
public class ClientEntity {
    @Id
    private String id;
    private String name;
    private String dni;
    private ClientType type;

    public enum ClientType {
        PERSONAL, EMPRESARIAL
    }
}