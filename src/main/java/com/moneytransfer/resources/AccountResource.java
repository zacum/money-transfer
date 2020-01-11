package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.services.AccountService;

import javax.money.UnknownCurrencyException;
import java.util.Optional;

import static spark.Spark.*;

public class AccountResource {

    @Inject
    private AccountService accountService;

    public void run() {
        post("/account", (request, response) -> {
            AccountCreateRequest accountCreateRequest = null;
            try {
                accountCreateRequest = new Gson().fromJson(request.body(), AccountCreateRequest.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                halt(400, "Invalid JSON");
            }
            AccountResponse account = null;
            try {
                account = accountService.createAccount(accountCreateRequest);
            } catch (UnknownCurrencyException e) {
                halt(400, "Invalid currency code");
            }
            return new Gson().toJson(account);
        });

        get("/account/:id", (request, response) -> {
            Long accountId = null;
            try {
                accountId = Long.valueOf(request.params(":id"));
            } catch (NumberFormatException nfe) {
                halt(400, "Account id is not numerical");
            }
            Optional<AccountResponse> accountOpt = accountService.getAccount(accountId);
            if (accountOpt.isEmpty()) {
                halt(404);
            }
            return new Gson().toJson(accountOpt.get());
        });
    }

}
