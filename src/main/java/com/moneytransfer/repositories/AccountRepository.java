package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.exceptions.IllegalTransactionAccountException;

import java.util.List;
import java.util.Optional;

public class AccountRepository {

    @Inject
    private Database database;

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

    public void update(Account account, Transaction transaction) {
        database
                .transaction(transaction)
                .table("account")
                .update(account);
    }

    public Account getAccountOrThrowNotFound(Long accountId, Transaction transaction) {
        return database
                .transaction(transaction)
                .table("account")
                .where("id=?", accountId)
                .results(Account.class)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalTransactionAccountException("Account id is not found"));
    }

}
