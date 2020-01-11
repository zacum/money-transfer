package com.moneytransfer;

import com.google.inject.AbstractModule;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.repositories.TransactionRepository;
import com.moneytransfer.services.AccountService;
import com.moneytransfer.services.TransactionService;

public class GuiceConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).asEagerSingleton();
        bind(AccountRepository.class).asEagerSingleton();

        bind(TransactionService.class).asEagerSingleton();
        bind(TransactionRepository.class).asEagerSingleton();
    }

}
