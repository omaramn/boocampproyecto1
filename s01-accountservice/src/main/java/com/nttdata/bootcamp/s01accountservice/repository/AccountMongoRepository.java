package com.nttdata.bootcamp.s01accountservice.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.nttdata.bootcamp.s01accountservice.document.AccountDocument;
import com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput.TypeEnum;
import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;

public interface AccountMongoRepository extends MongoRepository<AccountDocument, String>{
	
	AccountDocument findById(ObjectId objectId);
	
	List<AccountDetails> findByOwnerClientsContains(List<ObjectId> collect);

	List<AccountDocument> findByOwnerClientsContainsAndType(ObjectId objectId, TypeEnum corriente);

	List<AccountDetails> findAllById(List<ObjectId> idList);





}
