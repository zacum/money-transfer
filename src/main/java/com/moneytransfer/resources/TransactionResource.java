package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.services.TransactionService;
import spark.Request;

import javax.money.UnknownCurrencyException;

import static spark.Spark.halt;
import static spark.Spark.post;

public class TransactionResource {

    @Inject
    private TransactionService transactionService;

    public void run() {
        post("/transaction", (request, response) -> {
            TransactionCreateRequest transactionCreateRequest = getTransactionCreateRequest(request);
            createTransaction(transactionCreateRequest);
            response.status(201);
            return "";
        });
    }

    private TransactionCreateRequest getTransactionCreateRequest(Request request) {
        try {
            return new Gson().fromJson(request.body(), TransactionCreateRequest.class);
        } catch (JsonSyntaxException e) {
            halt(400, "Invalid JSON");
            throw e;
        }
    }

    private void createTransaction(TransactionCreateRequest transactionCreateRequest) {
        try {
            transactionService.transferMoney(transactionCreateRequest);
        } catch (UnknownCurrencyException e) {
            halt(400, "Invalid currency code");
            throw e;
        }
    }

}
