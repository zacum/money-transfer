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
            saveReceivables(receivables, transactionQuery, getAccount(receivables, transactionQuery));
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
            savePayables(payables, transactionQuery, getAccount(payables, transactionQuery));
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    public void save(Payables payables, Receivables receivables) {
        Transaction transaction = database.startTransaction();
        try {
            Query transactionQuery = database.transaction(transaction);
            Account accountTo = getAccount(receivables, transactionQuery);
            Account accountFrom = getAccount(payables, transactionQuery);
            receivables = saveReceivables(receivables, transactionQuery, accountTo);
            payables = savePayables(payables, transactionQuery, accountFrom);
            saveTransfers(receivables, payables, transactionQuery);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
    }

    private Receivables saveReceivables(Receivables receivables, Query transactionQuery, Account account) {
        transactionQuery.table("account").update(account);
        transactionQuery.table("receivables").generatedKeyReceiver(receivables, "id").insert(receivables);
        return receivables;
    }

    private Payables savePayables(Payables payables, Query transactionQuery, Account account) {
        transactionQuery.table("account").update(account);
        transactionQuery.table("payables").generatedKeyReceiver(payables, "id").insert(payables);
        return payables;
    }

    private Transfers saveTransfers(Receivables receivables, Payables payables, Query transactionQuery) {
        Transfers transfers = new Transfers();
        transfers.setReceivablesId(receivables.getId());
        transfers.setPayablesId(payables.getId());
        transactionQuery.table("transfers").generatedKeyReceiver(transfers, "id").insert(transfers);
        return transfers;
    }

    private Account getAccount(Receivables receivables, Query transactionQuery) {
        List<Account> accounts = transactionQuery.table("account").where("id=?", receivables.getAccountId()).results(Account.class);
        if (accounts.size() != 1) {
            throw new IllegalTransactionAccountException("Account " + receivables.getAccountId() + " not fount");
        }
        Account account = accounts.stream().findFirst().orElseThrow();
        account.addMoney(Money.of(receivables.getAmount(), receivables.getCurrency()));
        return account;
    }

    private Account getAccount(Payables payables, Query transactionQuery) {
        List<Account> accounts = transactionQuery.table("account").where("id=?", payables.getAccountId()).results(Account.class);
        if (accounts.size() != 1) {
            throw new IllegalTransactionAccountException("Account " + payables.getAccountId() + " not fount");
        }
        Account account = accounts.stream().findFirst().orElseThrow();
        account.subtractMoney(Money.of(payables.getAmount(), payables.getCurrency()));
        if (account.getAmount().signum() < 0) {
            throw new IllegalTransactionBalanceException("Account " + payables.getAccountId() + " does not have sufficient funds");
        }
        return account;
    }

}
