package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.exceptions.IllegalTransactionAccountException;
import com.moneytransfer.exceptions.IllegalTransactionNegativeAmountException;
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
            before("/*", ((request, response) -> response.status(201)));
            post("/payables", (request, response) -> {
                PayablesCreateRequest payablesCreateRequest = gson.fromJson(request.body(), PayablesCreateRequest.class);
                if (payablesCreateRequest.getAmount().signum() <= 0) {
                    throw new IllegalTransactionNegativeAmountException("Amount needs to be positive");
                }
                return transactionService.createPayables(payablesCreateRequest);
            }, gson::toJson);
            post("/receivables", (request, response) -> {
                ReceivablesCreateRequest receivablesCreateRequest = gson.fromJson(request.body(), ReceivablesCreateRequest.class);
                if (receivablesCreateRequest.getAmount().signum() <= 0) {
                    throw new IllegalTransactionNegativeAmountException("Amount needs to be positive");
                }
                return transactionService.createReceivables(receivablesCreateRequest);
            }, gson::toJson);
            post("/transfers", (request, response) -> {
                TransfersCreateRequest transfersCreateRequest = gson.fromJson(request.body(), TransfersCreateRequest.class);
                if (transfersCreateRequest.getAmount().signum() <= 0) {
                    throw new IllegalTransactionNegativeAmountException("Amount needs to be positive");
                }
                if (transfersCreateRequest.getToAccountId().equals(transfersCreateRequest.getFromAccountId())) {
                    throw new IllegalTransactionAccountException("Cannot transfer money to and from the same account");
                }
                return transactionService.createTransfers(transfersCreateRequest);
            }, gson::toJson);
        });
    }

}
