package com.moneytransfer.repositories;

import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.entities.AccountEntity;

public class AccountRepository {

    public AccountEntity save(AccountCreateRequest accountCreateRequest) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setName(accountCreateRequest.getName());
        accountEntity.setCurrency(accountCreateRequest.getCurrency());
        return accountEntity;
    }

}
