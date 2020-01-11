package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.services.TransactionService;

import static spark.Spark.halt;
import static spark.Spark.post;

public class TransactionResource {

    @Inject
    private TransactionService transactionService;

    public void run() {
        post("/transaction", (request, response) -> {
            String json = null;
            try {
                TransactionCreateRequest transactionCreateRequest = new Gson().fromJson(request.body(), TransactionCreateRequest.class);
                json = new Gson().toJson(transactionService.transferMoney(transactionCreateRequest));
            } catch (Exception e) {
                halt(400, e.toString());
            }
            return json;
        });
    }

}
