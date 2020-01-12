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

        database.table("account").generatedKeyReceiver(account, "id").insert(account);
        return account;
    }

    public List<Account> getAccounts() {
        return database.table("account").orderBy("id").results(Account.class);
    }

    public Optional<Account> get(Long accountId) {
        List<Account> accounts = database.table("account").where("id=?", accountId).results(Account.class);
        if (accounts.size() != 1) {
            return Optional.empty();
        }
        return accounts.stream().findFirst();
    }

}
