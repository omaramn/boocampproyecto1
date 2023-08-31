package com.nttdata.bootcamp.s01accountservice.service;

import java.util.List;

import com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput;
import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;

public interface AccountService {

	AccountDetails accountsAccountIdGet(String accountId);
	
	List<AccountDetails> accountsByListPost(List<String> requestBody);
	
	AccountDetails accountsPost(AccountCreateInput accountCreateInput);

	AccountDetails accountsAccountIdAddSignersPost(String accountId, List<String> requestBody);
}
