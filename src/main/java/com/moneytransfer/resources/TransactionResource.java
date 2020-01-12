package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.exceptions.IllegalTransactionNegativeAmountException;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.services.TransactionService;

import static spark.Spark.post;

public class TransactionResource {

    @Inject
    private TransactionService transactionService;

    public void run() {
        post("/transaction", (request, response) -> {
            TransactionCreateRequest transactionCreateRequest = new Gson().fromJson(request.body(), TransactionCreateRequest.class);
            if (transactionCreateRequest.getAmount().signum() < 0) {
                throw new IllegalTransactionNegativeAmountException("Amount cannot be negative");
            }
            transactionService.transferMoney(transactionCreateRequest);
            response.status(201);
            return "";
        });
    }

}
