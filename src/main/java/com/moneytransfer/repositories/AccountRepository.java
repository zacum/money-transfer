package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;

import java.util.List;

public class AccountRepository {

    @Inject
    private Database database;

    public Account save(Account account) {
        database.table("account").generatedKeyReceiver(account, "id").insert(account);
        return account;
    }

    public List<Account> getAccounts() {
        return database.table("account").orderBy("id").results(Account.class);
    }

    public List<Account> get(Long accountId) {
        return database.table("account").where("id=?", accountId).results(Account.class);
    }

}
