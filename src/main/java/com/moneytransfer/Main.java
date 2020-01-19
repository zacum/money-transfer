package com.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.resources.AccountResource;
import com.moneytransfer.resources.DefaultResource;
import com.moneytransfer.resources.TransactionResource;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceConfiguration());
        injector.getInstance(DefaultResource.class).run();
        injector.getInstance(AccountResource.class).run();
        injector.getInstance(TransactionResource.class).run();

        // FIXME: Warm up Norm connection to H2
        // FIXME: Otherwise the first request will not be persisted to DB
        injector.getInstance(AccountRepository.class).getAccounts();
    }

}
