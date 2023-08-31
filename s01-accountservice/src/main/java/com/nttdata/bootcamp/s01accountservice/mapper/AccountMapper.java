package com.nttdata.bootcamp.s01accountservice.mapper;

import java.math.BigDecimal;

import com.nttdata.bootcamp.s01accountservice.document.AccountDocument;
import com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput;
import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;

public class AccountMapper {	
	
    public static AccountDocument mapDtoToDocument(AccountDetails accountDetails) {
        return AccountDocument.builder()
                .id(accountDetails.getId())
                .type(accountDetails.getType())
                .ownerClients(accountDetails.getOwnerClients())
                .signClients(accountDetails.getSignClients())
                .balance(accountDetails.getBalance())
                .build();
    }

    public static AccountDetails mapDocumentToDto(AccountDocument accountDocument) {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setId(accountDocument.getId());
        accountDetails.setType(accountDocument.getType());
        accountDetails.setOwnerClients(accountDocument.getOwnerClients());
        accountDetails.setSignClients(accountDocument.getSignClients());
        accountDetails.setBalance(accountDocument.getBalance());
        return accountDetails;        
    }
    
    public static AccountDocument mapCreateInputToDocument(AccountCreateInput accountCreateInput) {
        return AccountDocument.builder()
                .type(mapAccountCreateInputTypeEnumToAccountDetailsTypeEnum(accountCreateInput.getType()))
                .ownerClients(accountCreateInput.getOwnerClients())
                .signClients(accountCreateInput.getSignClients())
                .balance(BigDecimal.ZERO)  // Set initial balance as needed
                .build();
    }
    
    public static AccountCreateInput.TypeEnum mapAccountDetailsTypeEnumToAccountCreateInputTypeEnum(AccountDetails.TypeEnum accountDetailsTypeEnum) {
        switch (accountDetailsTypeEnum) {
            case AHORRO:
                return AccountCreateInput.TypeEnum.AHORRO;
            case CORRIENTE:
                return AccountCreateInput.TypeEnum.CORRIENTE;
            case PLAZOFIJO:
                return AccountCreateInput.TypeEnum.PLAZOFIJO;
            default:
                throw new IllegalArgumentException("Unsupported mapping for AccountDetails.TypeEnum value: " + accountDetailsTypeEnum);
        }
    }
    
    public static AccountDetails.TypeEnum mapAccountCreateInputTypeEnumToAccountDetailsTypeEnum(AccountCreateInput.TypeEnum accountCreateInputTypeEnum) {
        switch (accountCreateInputTypeEnum) {
            case AHORRO:
                return AccountDetails.TypeEnum.AHORRO;
            case CORRIENTE:
                return AccountDetails.TypeEnum.CORRIENTE;
            case PLAZOFIJO:
                return AccountDetails.TypeEnum.PLAZOFIJO;
            default:
                throw new IllegalArgumentException("Unsupported mapping for AccountCreateInput.TypeEnum value: " + accountCreateInputTypeEnum);
        }
    }
}
