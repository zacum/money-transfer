package com.moneytransfer.repositories;

import com.moneytransfer.entities.AccountEntity;
import com.moneytransfer.models.account.AccountCreateRequest;
import org.javamoney.moneta.Money;

public class AccountRepository {

    public AccountEntity save(AccountCreateRequest accountCreateRequest) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setName(accountCreateRequest.getName());
        accountEntity.setMoney(Money.of(0, accountCreateRequest.getCurrency()));
        return accountEntity;
    }

}
