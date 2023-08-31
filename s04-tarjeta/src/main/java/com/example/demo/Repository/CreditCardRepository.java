package com.example.demo.Repository;

import com.example.demo.Entity.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
    Mono<CreditCard> findByCardNumber(String cardNumber);

}