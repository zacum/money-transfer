package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Transaction;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.repositories.TransactionRepository;

public class TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    public String transferMoney(TransactionCreateRequest transactionCreateRequest) {
        Transaction transaction = transactionRepository.save(transactionCreateRequest);

        System.out.println(transaction.toString());

        return "ok";
    }

}
