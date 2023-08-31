package com.example.creditservice.servicio;


import com.example.creditservice.document.CreditEntity;
import com.example.creditservice.maper.MaperCredit;
import com.example.creditservice.model.Credit;
import com.example.creditservice.repository.RepositoryCredit;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCredit {
    @Autowired
    private RepositoryCredit repository;
    public Credit createCredit(Credit credit){
        CreditEntity credit1 = MaperCredit.dtotoCreditEntity(credit);
        CreditEntity saveEntity = repository.save(credit1);
        return MaperCredit.entityToDto(saveEntity);
    }
   public Credit getCreditById(String clientId){

       Optional<CreditEntity> optionalEntity = repository.findById( new ObjectId(clientId));
       if (optionalEntity.isPresent()) {
           CreditEntity entity = optionalEntity.get();
           return MaperCredit.entityToDto(entity);
       } else {
           return null;
       }
    }


}
