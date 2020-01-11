package com.moneytransfer;

import com.google.inject.AbstractModule;
import com.moneytransfer.services.AccountService;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).asEagerSingleton();
    }

}
