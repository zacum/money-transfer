package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Optional;

public class AccountRepository {

    @Inject
    private Database database;

    public Account save(AccountCreateRequest accountCreateRequest) {
        Account account = new Account();
        account.setName(accountCreateRequest.getName());
        account.setMoney(Money.of(0, accountCreateRequest.getCurrency()));

        database.table("account").insert(account);
        return database.where("name=?", account.getName()).results(Account.class).get(0);
    }

    public Optional<Account> get(Long accountId) {
        List<Account> accounts = database.where("id=?", accountId).results(Account.class);
        if (accounts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(accounts.get(0));
    }

}
