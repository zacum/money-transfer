package com.moneytransfer;

import com.google.inject.AbstractModule;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.services.AccountService;

public class GuiceConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).asEagerSingleton();
        bind(AccountRepository.class).asEagerSingleton();
    }

}
