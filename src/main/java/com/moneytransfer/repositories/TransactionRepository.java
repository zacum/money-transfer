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
import com.moneytransfer.exceptions.IllegalTransactionBalanceException;
import org.javamoney.moneta.Money;

import java.util.List;

public class TransactionRepository {

    @Inject
    private Database database;

    public void save(Receivables receivables) {
        Transaction transaction = database.startTransaction();
        try {
            Query transactionQuery = database.transaction(transaction);
            saveReceivables(receivables, transactionQuery);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    public void save(Payables payables) {
        Transaction transaction = database.startTransaction();
        try {
            Query transactionQuery = database.transaction(transaction);
            savePayables(payables, transactionQuery);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    public void save(Receivables receivables, Payables payables) {
        Transaction transaction = database.startTransaction();
        try {
            Query transactionQuery = database.transaction(transaction);
            Receivables receivablesCreated = saveReceivables(receivables, transactionQuery);
            Payables payablesCreated = savePayables(payables, transactionQuery);
            saveTransfers(receivablesCreated, payablesCreated, transactionQuery);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    private Receivables saveReceivables(Receivables receivables, Query transactionQuery) {
        List<Account> accountsTo = transactionQuery.table("account").where("id=?", receivables.getAccountId()).results(Account.class);
        if (accountsTo.size() != 1) {
            throw new IllegalTransactionAccountException("Account " + receivables.getAccountId() + " not fount");
        }
        Account accountTo = accountsTo.stream().findFirst().orElseThrow();
        accountTo.addMoney(Money.of(receivables.getAmount(), receivables.getCurrency()));
        transactionQuery.table("account").update(accountTo);
        transactionQuery.table("receivables").generatedKeyReceiver(accountTo, "id").insert(receivables);
        return receivables;
    }

    private Payables savePayables(Payables payables, Query transactionQuery) {
        List<Account> accountsFrom = transactionQuery.table("account").where("id=?", payables.getAccountId()).results(Account.class);
        if (accountsFrom.size() != 1) {
            throw new IllegalTransactionAccountException("Account " + payables.getAccountId() + " not fount");
        }
        Account accountFrom = accountsFrom.stream().findFirst().orElseThrow();
        accountFrom.subtractMoney(Money.of(payables.getAmount(), payables.getCurrency()));
        if (accountFrom.getAmount().signum() < 0) {
            throw new IllegalTransactionBalanceException("Account " + payables.getAccountId() + " does not have sufficient funds");
        }
        transactionQuery.table("account").update(accountFrom);
        transactionQuery.table("payables").generatedKeyReceiver(accountFrom, "id").insert(payables);
        return payables;
    }

    private Transfers saveTransfers(Receivables receivables, Payables payables, Query transactionQuery) {
        Transfers transfers = new Transfers();
        transfers.setReceivablesId(receivables.getId());
        transfers.setPayablesId(payables.getId());
        transactionQuery.table("transfers").generatedKeyReceiver(transfers, "id").insert(transfers);
        return transfers;
    }

}
