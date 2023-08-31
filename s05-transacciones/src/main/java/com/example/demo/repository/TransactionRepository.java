package com.example.demo.repository;

import com.example.demo.entitys.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.List;

public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity,String> {

    List<TransactionEntity> findByClientId(String clientId);
}
