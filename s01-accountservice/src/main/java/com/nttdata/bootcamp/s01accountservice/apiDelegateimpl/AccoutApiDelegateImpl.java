package com.nttdata.bootcamp.s01accountservice.apiDelegateimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nttdata.bootcamp.s01accountservice.api.AccountsApiDelegate;
import com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput;
import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;
import com.nttdata.bootcamp.s01accountservice.service.AccountService;


@Component
public class AccoutApiDelegateImpl implements AccountsApiDelegate{
	
	@Autowired
	AccountService accountService;

	@Override
	public ResponseEntity<AccountDetails> accountsAccountIdGet(String accountId) {
		return ResponseEntity.ok(accountService.accountsAccountIdGet(accountId));
	}

	@Override
	public ResponseEntity<List<AccountDetails>> accountsByListPost(List<String> requestBody) {
		return ResponseEntity.ok(accountService.accountsByListPost(requestBody));
	}
	
	@Override
	public ResponseEntity<AccountDetails> accountsClientIdGet(String clientId) {
		return AccountsApiDelegate.super.accountsClientIdGet(clientId);
	}

	@Override
	public ResponseEntity<AccountDetails> accountsPost(AccountCreateInput accountCreateInput) {
		return ResponseEntity.ok(accountService.accountsPost(accountCreateInput));
	}

	@Override
	public ResponseEntity<AccountDetails> accountsAccountIdAddSignersPost(String accountId, List<String> requestBody) {
		return ResponseEntity.ok(accountService.accountsAccountIdAddSignersPost(accountId, requestBody));
	}
	
	

}
