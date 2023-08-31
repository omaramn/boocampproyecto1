package com.example.demo.Service;

import com.example.demo.Entity.CreditCard;
import com.example.demo.Repository.CreditCardRepository;
import com.example.demo.TransactionRestClient;
import com.example.demo.model.CreditCardBalance;
import com.example.demo.model.CreditCardPayment;
import com.example.demo.model.CreditCardPurchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.demo.DTOS.Transaction;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRestClient transactionRestClient;

    private static final SecureRandom random = new SecureRandom();

    public CreditCard findCardByNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber).block();
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        if (creditCard.getCardNumber() == null || creditCard.getCardNumber().isEmpty()) {
            creditCard.setCardNumber(generateUniqueCardNumber());
        }
        return creditCardRepository.save(creditCard).block();
    }

    public void makePayment(String cardNumber, Double amount) {
        CreditCard creditCard = findCardByNumber(cardNumber);
        if (creditCard != null) {
            creditCard.setBalance(creditCard.getBalance() - amount);
            creditCard.setAvailableCredit(creditCard.getLimit() - creditCard.getBalance());
            saveCreditCard(creditCard);

            // Enviar transacción
            sendTransactionToService(creditCard.getClientId(), "CREDIT_CARD", "Payment", amount);
        }
    }

    public void registerPurchase(String cardNumber, Double amount) {
        CreditCard creditCard = findCardByNumber(cardNumber);
        if (creditCard != null && creditCard.getAvailableCredit() >= amount) {
            creditCard.setBalance(creditCard.getBalance() + amount);
            creditCard.setAvailableCredit(creditCard.getLimit() - creditCard.getBalance());
            saveCreditCard(creditCard);

            // Enviar transacción
            sendTransactionToService(creditCard.getClientId(), "CREDIT_CARD", "Purchase", amount);
        }
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber();
        } while (isCardNumberExist(cardNumber));
        return cardNumber;
    }

    private String generateRandomCardNumber() {
        StringBuilder generatedNumber = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int randomNum = random.nextInt(10);
            generatedNumber.append(randomNum);
        }
        return generatedNumber.toString();
    }

    private boolean isCardNumberExist(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber) != null;
    }
    private void sendTransactionToService(String clientId, String type, String description, Double amount) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId("999999");
            transaction.setClientId(clientId);
            transaction.setTransactionType(Transaction.TransactionType.valueOf(type));
            transaction.setReferenceId(description);
            transaction.setAmount(amount);
            transaction.setDescription(description);

            transaction.setTransactionDate(OffsetDateTime.parse("2023-08-30T21:43:45.983+00:00"));




            transactionRestClient.sendTransaction(transaction);
        } catch(Exception e) {
            // Ignoramos cualquier error al intentar comunicarnos con el servicio de transacciones
        }
    }
    }
