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
import java.util.List;
import java.util.Optional;

public class TransactionRepository {

    @Inject
    private Database database;

    public Payables save(Payables payables) {
        Transaction transaction = database.startTransaction();
        try {
            Account account = withdraw(payables, transaction);
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
            Account account = deposit(receivables, transaction);
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
            Account accountFrom = withdraw(payables, transaction);
            Account accountTo = deposit(receivables, transaction);
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

    private Account deposit(Receivables receivables, Transaction transaction) {
        Account account = getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(receivables.getAmount(), receivables.getCurrency());
        if (!receivables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.add(modification));
        return account;
    }

    private Account withdraw(Payables payables, Transaction transaction) {
        Account account = getAccountOrThrowNotFound(payables.getAccountId(), transaction);
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(payables.getAmount(), payables.getCurrency());
        if (!payables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.subtract(modification));
        if (account.getAmount().signum() < 0) {
            throw new IllegalTransactionBalanceException("Paying account does not have sufficient funds");
        }
        return account;
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

    private Payables savePayables(Payables payables, Transaction transaction, Account account) {
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

    public List<Payables> getPayables() {
        return database
                .table("payables")
                .orderBy("id")
                .results(Payables.class);
    }

    public List<Receivables> getReceivables() {
        return database
                .table("receivables")
                .orderBy("id")
                .results(Receivables.class);
    }

    public List<Transfers> getTransfers() {
        return database
                .table("transfers")
                .orderBy("id")
                .results(Transfers.class);
    }

    public Optional<Payables> getPayables(Long payablesId) {
        return database
                .table("payables")
                .where("id=?", payablesId)
                .results(Payables.class)
                .stream()
                .findFirst();
    }

    public Optional<Receivables> getReceivables(Long receivablesId) {
        return database
                .table("receivables")
                .where("id=?", receivablesId)
                .results(Receivables.class)
                .stream()
                .findFirst();
    }

}
