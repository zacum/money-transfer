package com.moneytransfer.repositories;

import com.moneytransfer.entities.TransactionEntity;
import com.moneytransfer.models.transaction.TransactionTransferRequest;
import org.javamoney.moneta.Money;

public class TransactionRepository {

    public TransactionEntity save(TransactionTransferRequest transactionTransferRequest) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setFromAccountId(transactionTransferRequest.getFromAccountId());
        transactionEntity.setToAccountId(transactionTransferRequest.getToAccountId());
        transactionEntity.setMoney(Money.of(transactionTransferRequest.getAmount(), transactionTransferRequest.getCurrency()));
        return transactionEntity;
    }

}
