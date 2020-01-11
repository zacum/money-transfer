package com.moneytransfer.repositories;

import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import org.javamoney.moneta.Money;

public class AccountRepository {

    public Account save(AccountCreateRequest accountCreateRequest) {
        Account account = new Account();
        account.setId(1L);
        account.setName(accountCreateRequest.getName());
        account.setMoney(Money.of(0, accountCreateRequest.getCurrency()));
        return account;
    }

}
