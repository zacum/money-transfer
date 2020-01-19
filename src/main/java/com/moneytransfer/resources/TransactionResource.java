package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.exceptions.SameAccountException;
import com.moneytransfer.exceptions.NegativeAmountTransactionException;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransfersCreateRequest;
import com.moneytransfer.services.TransactionService;

import static spark.Spark.*;

public class TransactionResource {

    @Inject
    private Gson gson;

    @Inject
    private TransactionService transactionService;

    public void run() {
        path("/transaction", () -> {
            post("/payables", (request, response) -> {
                PayablesCreateRequest payablesCreateRequest = gson.fromJson(request.body(), PayablesCreateRequest.class);
                if (payablesCreateRequest.getAmount().signum() <= 0) {
                    throw new NegativeAmountTransactionException("Amount needs to be positive");
                }
                response.status(201);
                return transactionService.createPayables(payablesCreateRequest);
            }, gson::toJson);
            post("/receivables", (request, response) -> {
                ReceivablesCreateRequest receivablesCreateRequest = gson.fromJson(request.body(), ReceivablesCreateRequest.class);
                if (receivablesCreateRequest.getAmount().signum() <= 0) {
                    throw new NegativeAmountTransactionException("Amount needs to be positive");
                }
                response.status(201);
                return transactionService.createReceivables(receivablesCreateRequest);
            }, gson::toJson);
            post("/transfers", (request, response) -> {
                TransfersCreateRequest transfersCreateRequest = gson.fromJson(request.body(), TransfersCreateRequest.class);
                if (transfersCreateRequest.getAmount().signum() <= 0) {
                    throw new NegativeAmountTransactionException("Amount needs to be positive");
                }
                if (transfersCreateRequest.getToAccountId().equals(transfersCreateRequest.getFromAccountId())) {
                    throw new SameAccountException("Cannot transfer money to and from the same account");
                }
                response.status(201);
                return transactionService.createTransfers(transfersCreateRequest);
            }, gson::toJson);
            get("/payables", (request, response) -> {
                // FIXME: This endpoint is for testing purposes only
                // FIXME: The production version of it needs to implement pagination, filtering, etc.
                response.status(200);
                return transactionService.getPayables();
            }, gson::toJson);
            get("/receivables", (request, response) -> {
                // FIXME: This endpoint is for testing purposes only
                // FIXME: The production version of it needs to implement pagination, filtering, etc.
                response.status(200);
                return transactionService.getReceivables();
            }, gson::toJson);
            get("/transfers", (request, response) -> {
                // FIXME: This endpoint is for testing purposes only
                // FIXME: The production version of it needs to implement pagination, filtering, etc.
                response.status(200);
                return transactionService.getTransfers();
            }, gson::toJson);
        });
    }

}
