package com.moneytransfer;

import com.google.inject.Inject;
import com.moneytransfer.services.AccountService;

import static spark.Spark.get;

public class RestAPI {

    @Inject
    private AccountService accountService;

    public void run() {
        get("/account", (request, response) -> accountService.createAccount("Hello World"));
    }

}
