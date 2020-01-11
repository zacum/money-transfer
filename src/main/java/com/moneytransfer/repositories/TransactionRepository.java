package com.moneytransfer.repositories;

import com.moneytransfer.entities.Transaction;
import com.moneytransfer.models.transaction.TransactionTransferRequest;
import org.javamoney.moneta.Money;

public class TransactionRepository {

    public Transaction save(TransactionTransferRequest transactionTransferRequest) {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setFromAccountId(transactionTransferRequest.getFromAccountId());
        transaction.setToAccountId(transactionTransferRequest.getToAccountId());
        transaction.setMoney(Money.of(transactionTransferRequest.getAmount(), transactionTransferRequest.getCurrency()));
        return transaction;
    }

}
