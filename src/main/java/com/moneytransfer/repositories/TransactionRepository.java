package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.IllegalTransactionBalanceException;
import org.javamoney.moneta.Money;

public class TransactionRepository {

    @Inject
    private Database database;

    public Payables save(Payables payables, Account account) {
        Transaction transaction = database.startTransaction();
        try {
            payables = savePayables(payables, transaction, account);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return payables;
    }

    public Receivables save(Receivables receivables, Account account) {
        Transaction transaction = database.startTransaction();
        try {
            receivables = saveReceivables(receivables, transaction, account);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return receivables;
    }

    public Transfers save(Payables payables, Receivables receivables, Transfers transfers, Account accountFrom, Account accountTo) {
        Transaction transaction = database.startTransaction();
        try {
            payables = savePayables(payables, transaction, accountFrom);
            receivables = saveReceivables(receivables, transaction, accountTo);
            transfers.setPayablesId(payables.getId());
            transfers.setReceivablesId(receivables.getId());
            transfers = saveTransfers(transfers, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return transfers;
    }

    private Payables savePayables(Payables payables, Transaction transaction, Account account) {
        account.subtractMoney(Money.of(payables.getAmount(), payables.getCurrency()));
        if (account.getAmount().signum() < 0) {
            throw new IllegalTransactionBalanceException("Paying account does not have sufficient funds");
        }
        database
                .transaction(transaction)
                .table("account")
                .update(account);
        database
                .transaction(transaction)
                .table("payables")
                .generatedKeyReceiver(payables, "id")
                .insert(payables);
        return payables;
    }

    private Receivables saveReceivables(Receivables receivables, Transaction transaction, Account account) {
        account.addMoney(Money.of(receivables.getAmount(), receivables.getCurrency()));
        database
                .transaction(transaction)
                .table("account")
                .update(account);
        database
                .transaction(transaction)
                .table("receivables")
                .generatedKeyReceiver(receivables, "id")
                .insert(receivables);
        return receivables;
    }

    private Transfers saveTransfers(Transfers transfers, Transaction transaction) {
        database
                .transaction(transaction)
                .table("transfers")
                .generatedKeyReceiver(transfers, "id")
                .insert(transfers);
        return transfers;
    }

}
