package com.moneytransfer.repositories;

import com.moneytransfer.entities.Transaction;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import org.javamoney.moneta.Money;

public class TransactionRepository {

    public Transaction save(TransactionCreateRequest transactionCreateRequest) {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setFromAccountId(transactionCreateRequest.getFromAccountId());
        transaction.setToAccountId(transactionCreateRequest.getToAccountId());
        transaction.setMoney(Money.of(transactionCreateRequest.getAmount(), transactionCreateRequest.getCurrency()));
        return transaction;
    }

}
