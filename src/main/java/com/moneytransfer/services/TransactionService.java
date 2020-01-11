package com.moneytransfer.services;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.entities.TransactionEntity;
import com.moneytransfer.models.transaction.TransactionTransferRequest;
import com.moneytransfer.repositories.TransactionRepository;
import spark.Request;
import spark.Response;

public class TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    public String transferMoney(Request request, Response response) {
        TransactionTransferRequest transactionTransferRequest = new Gson().fromJson(request.body(), TransactionTransferRequest.class);

        TransactionEntity transactionEntity = transactionRepository.save(transactionTransferRequest);

        System.out.println(transactionEntity.toString());

        return new Gson().toJson("ok");
    }

}
