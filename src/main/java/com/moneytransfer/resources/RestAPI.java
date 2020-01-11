package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.services.AccountService;
import com.moneytransfer.services.TransactionService;

import static spark.Spark.get;
import static spark.Spark.post;

public class RestAPI {

    @Inject
    private AccountService accountService;

    @Inject
    private TransactionService transactionService;

    public void run() {
        post("/account", (request, response) -> {
            AccountCreateRequest accountCreateRequest = new Gson().fromJson(request.body(), AccountCreateRequest.class);
            return accountService.createAccount(accountCreateRequest);
        });
        get("/account/:id", (request, response) -> {
            Long accountId = Long.valueOf(request.params(":id"));
            return accountService.getAccount(accountId);
        });
        post("/transaction", (request, response) -> {
            TransactionCreateRequest transactionCreateRequest = new Gson().fromJson(request.body(), TransactionCreateRequest.class);
            return transactionService.transferMoney(transactionCreateRequest);
        });
    }

}
