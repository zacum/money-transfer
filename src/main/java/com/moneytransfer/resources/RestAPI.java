package com.moneytransfer.resources;

import com.google.inject.Inject;
import com.moneytransfer.services.AccountService;
import com.moneytransfer.services.TransactionService;

import static spark.Spark.post;

public class RestAPI {

    @Inject
    private AccountService accountService;

    @Inject
    private TransactionService transactionService;

    public void run() {
        post("/account", (request, response) -> accountService.createAccount(request, response));
    }

}
