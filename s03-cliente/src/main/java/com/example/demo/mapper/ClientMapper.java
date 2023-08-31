package com.example.demo.mapper;

import com.example.demo.entitys.ClientEntity;
import com.example.demo.model.Client;

public class ClientMapper {

    // Convertir desde DTO a Entidad
    public static ClientEntity dtoToEntity(Client clientDto) {
        return ClientEntity.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .dni(clientDto.getDni())
                .type(dtoTypeToEntityType(clientDto.getType()))
                .build();
    }

    private static ClientEntity.ClientType dtoTypeToEntityType(Client.TypeEnum typeEnum) {
        if (typeEnum == null) return null;
        switch (typeEnum) {
            case PERSONAL:
                return ClientEntity.ClientType.PERSONAL;
            case EMPRESARIAL:
                return ClientEntity.ClientType.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + typeEnum);
        }
    }

    // Convertir desde Entidad a DTO
    public static Client entityToDto(ClientEntity entity) {
        return new Client()
                .id(entity.getId())
                .name(entity.getName())
                .dni(entity.getDni())
                .type(entityTypeToDtoType(entity.getType()));
    }

    private static Client.TypeEnum entityTypeToDtoType(ClientEntity.ClientType clientType) {
        if (clientType == null) return null;
        switch (clientType) {
            case PERSONAL:
                return Client.TypeEnum.PERSONAL;
            case EMPRESARIAL:
                return Client.TypeEnum.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + clientType);
        }
    }
}

