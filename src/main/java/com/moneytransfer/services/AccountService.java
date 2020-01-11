package com.moneytransfer.services;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.entities.AccountEntity;
import com.moneytransfer.repositories.AccountRepository;
import spark.Request;
import spark.Response;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public Object createAccount(Request request, Response response) {
        AccountCreateRequest accountCreateRequest = new Gson().fromJson(request.body(), AccountCreateRequest.class);
        AccountEntity accountEntity = accountRepository.save(accountCreateRequest);
        System.out.println(accountCreateRequest.toString());
        System.out.println(accountEntity.toString());
        return new Gson().toJson(accountEntity);
    }

}
