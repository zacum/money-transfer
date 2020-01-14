package com.moneytransfer.resources;

import com.google.gson.JsonSyntaxException;
import com.moneytransfer.exceptions.IllegalTransactionException;

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
        exception(IllegalTransactionException.class, (exception, request, response) -> {
            response.status(400);
            response.body(exception.getMessage());
        });
    }

}
