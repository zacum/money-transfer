package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.IllegalTransactionAccountException;
import com.moneytransfer.exceptions.IllegalTransactionBalanceException;
import org.javamoney.moneta.Money;

import javax.money.convert.MonetaryConversions;

public class TransactionRepository {

    @Inject
    private Database database;

    public Payables save(Payables payables) {
        Transaction transaction = database.startTransaction();
        try {
            Account account = getAccountOrThrowNotFound(payables.getAccountId(), transaction);
            payables = savePayables(payables, transaction, account);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return payables;
    }

    public Receivables save(Receivables receivables) {
        Transaction transaction = database.startTransaction();
        try {
            Account account = getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
            receivables = saveReceivables(receivables, transaction, account);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return receivables;
    }

    public Transfers save(Payables payables, Receivables receivables, Transfers transfers) {
        Transaction transaction = database.startTransaction();
        try {
            Account accountFrom = getAccountOrThrowNotFound(payables.getAccountId(), transaction);
            Account accountTo = getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
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
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(payables.getAmount(), payables.getCurrency());
        if (!payables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.subtract(modification));
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
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(receivables.getAmount(), receivables.getCurrency());
        if (!receivables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.add(modification));
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

    private Account getAccountOrThrowNotFound(Long accountId, Transaction transaction) {
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
