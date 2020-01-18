package com.moneytransfer.resources;

import com.google.gson.JsonSyntaxException;
import com.moneytransfer.exceptions.*;

import javax.money.UnknownCurrencyException;

import static spark.Spark.*;

public class DefaultResource {

    public void run() {
        before(((request, response) -> response.type("application/json")));
        internalServerError((request, response) -> "{\"message\":\"Internal Server Error\"}");
        exception(UnknownCurrencyException.class, (exception, request, response) -> {
            response.status(400);
            response.body("Invalid currency code");
        });
        exception(JsonSyntaxException.class, (exception, request, response) -> {
            response.status(400);
            response.body("Invalid JSON");
        });
        exception(NegativeAmountTransactionException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
        exception(AccountBalanceException.class, (exception, request, response) -> {
            response.status(409);
            response.body(exception.getMessage());
        });
        exception(AccountNotFoundException.class, (exception, request, response) -> {
            response.status(404);
            response.body(exception.getMessage());
        });
        exception(SameAccountException.class, (exception, request, response) -> {
            response.status(409);
            response.body(exception.getMessage());
        });
        exception(AccountException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
    }

}
