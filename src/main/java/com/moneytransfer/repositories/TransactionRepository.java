package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Query;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.IllegalTransactionAccountException;
import org.javamoney.moneta.Money;

import java.util.List;

public class TransactionRepository {

    @Inject
    private Database database;

    public void save(Receivables receivables) {
        Transaction transaction = database.startTransaction();
        try {
            Query transactionQuery = database.transaction(transaction);
            List<Account> accounts = transactionQuery.table("account").where("id=?", receivables.getAccountId()).results(Account.class);
            if (accounts.size() != 1) {
                throw new IllegalTransactionAccountException("Account " + receivables.getAccountId() + " not fount");
            }
            Account account = accounts.stream().findFirst().orElseThrow();
            account.addMoney(Money.of(receivables.getAmount(), receivables.getCurrency()));
            transactionQuery.table("account").update(account);
            transactionQuery.table("receivables").generatedKeyReceiver(account, "id").insert(receivables);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    public void save(Payables payables) {
        database.table("payables").insert(payables);
    }

    public void save(Transfers transfers) {
        database.table("transfers").insert(transfers);
    }

}
