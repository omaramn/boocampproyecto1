package com.example.creditservice.repository;

import com.example.creditservice.document.CreditEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface RepositoryCredit extends MongoRepository<CreditEntity,String> {

       Optional<CreditEntity> findById(ObjectId s);
}
