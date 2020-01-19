package com.moneytransfer;

import com.dieselpoint.norm.Database;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.repositories.TransactionRepository;
import com.moneytransfer.services.AccountService;
import com.moneytransfer.services.TransactionService;

public class GuiceConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        System.setProperty("norm.jdbcUrl", "jdbc:h2:mem:moneytransfer;INIT=runscript from './src/main/resources/schema.sql';database_to_upper=false");
        System.setProperty("norm.user", "");
        System.setProperty("norm.password", "");

        bind(Gson.class).asEagerSingleton();
        bind(Database.class).asEagerSingleton();

        bind(AccountService.class).asEagerSingleton();
        bind(AccountRepository.class).asEagerSingleton();

        bind(TransactionService.class).asEagerSingleton();
        bind(TransactionRepository.class).asEagerSingleton();
    }

}
