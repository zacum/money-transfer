package com.moneytransfer.resources;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.services.AccountService;
import spark.Request;

import javax.money.UnknownCurrencyException;
import java.util.Optional;

import static spark.Spark.*;

public class AccountResource {

    @Inject
    private AccountService accountService;

    public void run() {
        post("/account", (request, response) -> {
            AccountCreateRequest accountCreateRequest = getAccountCreateRequest(request);
            AccountResponse account = getAccountResponse(accountCreateRequest);
            return new Gson().toJson(account);
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

    private AccountCreateRequest getAccountCreateRequest(Request request) {
        try {
            return new Gson().fromJson(request.body(), AccountCreateRequest.class);
        } catch (JsonSyntaxException e) {
            halt(400, "Invalid JSON");
            throw e;
        }
    }

    private AccountResponse getAccountResponse(AccountCreateRequest accountCreateRequest) {
        try {
            return accountService.createAccount(accountCreateRequest);
        } catch (UnknownCurrencyException e) {
            halt(400, "Invalid currency code");
            throw e;
        }
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
