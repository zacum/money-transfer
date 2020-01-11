package com.moneytransfer.resources;

import com.google.inject.Inject;
import com.moneytransfer.services.AccountService;

import static spark.Spark.post;

public class RestAPI {

    @Inject
    private AccountService accountService;

    public void run() {
        post("/account", (request, response) -> accountService.createAccount(request, response));
    }

}
