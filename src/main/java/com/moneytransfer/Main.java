package com.moneytransfer;

import com.google.gson.JsonSyntaxException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.exceptions.IllegalTransactionException;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.resources.AccountResource;
import com.moneytransfer.resources.TransactionResource;

import javax.money.UnknownCurrencyException;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        before(((request, response) -> response.type("application/json")));
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

        System.setProperty("norm.jdbcUrl", "jdbc:h2:mem:moneytransfer;INIT=runscript from './src/main/resources/schema.sql';database_to_upper=false");
        System.setProperty("norm.user", "");
        System.setProperty("norm.password", "");

        Injector injector = Guice.createInjector(new GuiceConfiguration());
        injector.getInstance(AccountResource.class).run();
        injector.getInstance(TransactionResource.class).run();

        // FIXME: Warm up Norm connection to H2
        // FIXME: Otherwise the first request will not be persisted to DB
        injector.getInstance(AccountRepository.class).getAccounts();
    }

}
