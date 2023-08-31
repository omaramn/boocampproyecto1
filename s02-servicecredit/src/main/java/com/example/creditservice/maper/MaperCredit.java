package com.example.creditservice.maper;

import com.example.creditservice.document.CreditEntity;
import com.example.creditservice.model.Credit;

import java.math.BigDecimal;

public class MaperCredit {

    public static CreditEntity dtotoCreditEntity(Credit creditDto){
        return CreditEntity.builder().id(creditDto.getId())
                .type(dtoCrediTypeType(creditDto.getType()))
                .id_cliente(creditDto.getIdCliente())
                .amount(creditDto.getAmount().intValue())
                .build();
    }
    private static CreditEntity.creditType dtoCrediTypeType(Credit.TypeEnum typeEnum) {
        if (typeEnum == null) return null;
        switch (typeEnum) {
            case PERSONAL:
                return CreditEntity.creditType.PERSONAL;
            case EMPRESARIAL:
                return CreditEntity.creditType.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + typeEnum);
        }
    }
    public static Credit entityToDto(CreditEntity entity){
        return new Credit()
                .id(entity.getId())
                .type(entityCrediType(entity.getType()))
                .idCliente(entity.getId_cliente())
                .amount(BigDecimal.valueOf(entity.getAmount()));

    }
    private static Credit.TypeEnum entityCrediType(CreditEntity.creditType creditType ) {
        if (creditType == null) return null;
        switch (creditType) {
            case PERSONAL:
                return Credit.TypeEnum.PERSONAL;
            case EMPRESARIAL:
                return Credit.TypeEnum.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + creditType);
        }
    }
}
