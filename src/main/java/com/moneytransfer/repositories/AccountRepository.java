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

    public Account get(Long accountId) {
        Account account = new Account();
        account.setId(accountId);
        account.setName("Victor");
        account.setMoney(Money.of(0, "EUR"));
        return account;
    }

}
