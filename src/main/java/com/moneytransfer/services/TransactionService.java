package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.IllegalTransactionBalanceException;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransactionResponse;
import com.moneytransfer.models.transaction.TransfersCreateRequest;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.repositories.TransactionRepository;
import org.javamoney.moneta.Money;

import javax.money.convert.MonetaryConversions;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private TransactionRepository transactionRepository;

    public TransactionResponse createPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = getPayables(payablesCreateRequest);
        Transaction transaction = transactionRepository.getTransaction();
        try {
            Account account = withdraw(payables, transaction);
            accountRepository.update(account, transaction);
            payables = transactionRepository.save(payables, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(payables);
    }

    public TransactionResponse createReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = getReceivables(receivablesCreateRequest);
        Transaction transaction = transactionRepository.getTransaction();
        try {
            Account account = deposit(receivables, transaction);
            accountRepository.update(account, transaction);
            receivables = transactionRepository.save(receivables, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(receivables);
    }

    public TransactionResponse createTransfers(TransfersCreateRequest transfersCreateRequest) {
        Payables payables = getPayables(new PayablesCreateRequest(transfersCreateRequest));
        Receivables receivables = getReceivables(new ReceivablesCreateRequest(transfersCreateRequest));
        Transfers transfers = new Transfers();

        Transaction transaction = transactionRepository.getTransaction();
        try {
            Account accountFrom = withdraw(payables, transaction);
            Account accountTo = deposit(receivables, transaction);
            accountRepository.update(accountFrom, transaction);
            accountRepository.update(accountTo, transaction);
            payables = transactionRepository.save(payables, transaction);
            receivables = transactionRepository.save(receivables, transaction);
            transfers.setPayablesId(payables.getId());
            transfers.setReceivablesId(receivables.getId());
            transfers = transactionRepository.save(transfers, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(payables, receivables, transfers);
    }

    private Payables getPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = new Payables();
        payables.setAccountId(payablesCreateRequest.getAccountId());
        payables.setMoney(Money.of(payablesCreateRequest.getAmount(), payablesCreateRequest.getCurrency()));
        return payables;
    }

    private Receivables getReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = new Receivables();
        receivables.setAccountId(receivablesCreateRequest.getAccountId());
        receivables.setMoney(Money.of(receivablesCreateRequest.getAmount(), receivablesCreateRequest.getCurrency()));
        return receivables;
    }

    private Account deposit(Receivables receivables, Transaction transaction) {
        Account account = accountRepository.getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(receivables.getAmount(), receivables.getCurrency());
        if (!receivables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.add(modification));
        return account;
    }

    private Account withdraw(Payables payables, Transaction transaction) {
        Account account = accountRepository.getAccountOrThrowNotFound(payables.getAccountId(), transaction);
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

    public List<TransactionResponse> getPayables() {
        return transactionRepository.getPayables().stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getReceivables() {
        return transactionRepository.getReceivables().stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransfers() {
        return transactionRepository.getTransfers().stream()
                .map(transfers -> {
                    Optional<Payables> payables = transactionRepository.getPayables(transfers.getPayablesId());
                    Optional<Receivables> receivables = transactionRepository.getReceivables(transfers.getReceivablesId());
                    return new TransactionResponse(payables.get(), receivables.get(), transfers);
                })
                .collect(Collectors.toList());
    }

}
