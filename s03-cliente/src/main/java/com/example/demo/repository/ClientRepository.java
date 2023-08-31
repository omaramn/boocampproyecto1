package com.example.demo.repository;

import com.example.demo.entitys.ClientEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ClientRepository  extends ReactiveMongoRepository<ClientEntity,String> {
    @Query("{ '_id': { $in: ?0 } }")
    Flux<ClientEntity> findAllByGivenIds(List<ObjectId> ids);
}
