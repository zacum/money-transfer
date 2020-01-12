package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.services.AccountService;
import spark.Request;

import java.util.Optional;

import static spark.Spark.*;

public class AccountResource {

    @Inject
    private AccountService accountService;

    public void run() {
        post("/account", (request, response) -> {
            AccountCreateRequest accountCreateRequest = new Gson().fromJson(request.body(), AccountCreateRequest.class);
            AccountResponse account = accountService.createAccount(accountCreateRequest);
            response.status(201);
            return new Gson().toJson(account);
        });
        get("/account", (request, response) -> {
            // FIXME: This endpoint is for testing purposes only
            // FIXME: The production version of it needs to implement pagination
            return new Gson().toJson(accountService.getAccounts());
        });
        get("/account/:id", (request, response) -> {
            Long accountId = getAccountId(request);
            Optional<AccountResponse> accountOpt = accountService.getAccount(accountId);
            if (accountOpt.isEmpty()) {
                halt(404);
            }
            return new Gson().toJson(accountOpt.get());
        });
    }

    private Long getAccountId(Request request) {
        try {
            return Long.valueOf(request.params(":id"));
        } catch (NumberFormatException e) {
            halt(400, "Account id is not numerical");
            throw e;
        }
    }

}
