package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;

import java.util.List;
import java.util.Optional;

public class AccountRepository {

    @Inject
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Transaction getTransaction() {
        return database.startTransaction();
    }

    public Account save(Account account) {
        database
                .table("account")
                .generatedKeyReceiver(account, "id")
                .insert(account);
        return account;
    }

    public List<Account> getAccounts() {
        return database
                .table("account")
                .orderBy("id")
                .results(Account.class);
    }

    public Optional<Account> get(Long accountId) {
        return database
                .table("account")
                .where("id=?", accountId)
                .results(Account.class)
                .stream()
                .findFirst();
    }

    public Optional<Account> get(Long accountId, Transaction transaction) {
        return database
                .transaction(transaction)
                .table("account")
                .where("id=?", accountId)
                .results(Account.class)
                .stream()
                .findFirst();
    }

    public Account update(Account account, Transaction transaction) {
        database
                .transaction(transaction)
                .table("account")
                .update(account);
        return account;
    }

}
