package com.example.creditservice.delegate;


import com.example.creditservice.api.CreditsApiDelegate;
import com.example.creditservice.model.Credit;

import com.example.creditservice.servicio.ServiceCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class delagateimp implements CreditsApiDelegate {
    @Autowired
    private ServiceCredit serviceCredit;
    @Override
    public ResponseEntity<Credit> applyForCredit(Credit credit) {
        return new ResponseEntity<Credit>(serviceCredit.createCredit(credit), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Credit> getAllCreditsByClient(String clientId) {
        Credit credit = serviceCredit.getCreditById(clientId);
       return ResponseEntity.ok(credit);
    }
}
