package com.example.demo.mapper;

import com.example.demo.Entity.CreditCard;
import com.example.demo.model.CreditCardBalance;
import com.example.demo.model.CreditCardCreateInput;
import com.example.demo.model.CreditCardDetails;

import java.math.BigDecimal;

public class CreditCardMapper {


    public static CreditCardDetails toCreditCardDetails(CreditCard creditCard) {
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setId(creditCard.getId());
        creditCardDetails.setClientId(creditCard.getClientId());
        creditCardDetails.setAvailableCredit(new BigDecimal(creditCard.getAvailableCredit()));
        creditCardDetails.setBalance(new BigDecimal(creditCard.getBalance()));
        creditCardDetails.setLimit(new BigDecimal(creditCard.getLimit()));
        creditCardDetails.setCardNumber(creditCard.getCardNumber());
        return creditCardDetails;
    }


    public static CreditCard fromCreditCardCreateInput(CreditCardCreateInput creditCardCreateInput) {
        return CreditCard.builder()
                .clientId(creditCardCreateInput.getClientId())
                .limit(creditCardCreateInput.getLimit().doubleValue()) // Convertir BigDecimal a Double
                .cardNumber(creditCardCreateInput.getCardNumber())
                .balance(0.0) // balance inicial es 0
                .availableCredit(creditCardCreateInput.getLimit().doubleValue()) // Convertir BigDecimal a Double
                .build();
    }


    public static CreditCardBalance toCreditCardBalance(CreditCard creditCard) {
        CreditCardBalance creditCardBalance = new CreditCardBalance();
        creditCardBalance.setBalance(new BigDecimal(Double.toString(creditCard.getBalance())));
        creditCardBalance.setAvailableCredit(new BigDecimal(Double.toString(creditCard.getAvailableCredit())));
        return creditCardBalance;
    }
}