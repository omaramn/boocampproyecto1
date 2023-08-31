package com.example.demo.controlador;

import com.example.demo.Entity.CreditCard;
import com.example.demo.Service.CreditCardService;
import com.example.demo.api.CreditCardsApiDelegate;
import com.example.demo.mapper.CreditCardMapper;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
@Component
public class delegateCreditCard implements CreditCardsApiDelegate {

    @Autowired
    private CreditCardService creditCardService;

    @Override
    public ResponseEntity<CreditCardBalance> creditCardsCardNumberBalanceGet(String cardNumber) {
        CreditCard creditCard = creditCardService.findCardByNumber(cardNumber);
        if (creditCard != null) {
            return ResponseEntity.ok(CreditCardMapper.toCreditCardBalance(creditCard));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> creditCardsCardNumberPaymentPost(String cardNumber, CreditCardPayment creditCardPayment) {
        try {
            creditCardService.makePayment(cardNumber, creditCardPayment.getAmount().doubleValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> creditCardsCardNumberPurchasePost(String cardNumber, CreditCardPurchase creditCardPurchase) {
        try {
            creditCardService.registerPurchase(cardNumber, creditCardPurchase.getAmount().doubleValue());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<CreditCardDetails> creditCardsPost(CreditCardCreateInput creditCardCreateInput) {
        CreditCard creditCard = CreditCardMapper.fromCreditCardCreateInput(creditCardCreateInput);
        CreditCard savedCard = creditCardService.saveCreditCard(creditCard);
        return ResponseEntity.ok(CreditCardMapper.toCreditCardDetails(savedCard));
    }
}