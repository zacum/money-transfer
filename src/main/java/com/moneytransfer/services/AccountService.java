package com.moneytransfer.services;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.entities.AccountEntity;
import com.moneytransfer.models.account.AccountCreateResponse;
import com.moneytransfer.repositories.AccountRepository;
import spark.Request;
import spark.Response;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public String createAccount(Request request, Response response) {
        AccountCreateRequest accountCreateRequest = new Gson().fromJson(request.body(), AccountCreateRequest.class);

        AccountEntity accountEntity = accountRepository.save(accountCreateRequest);

        AccountCreateResponse accountCreateResponse = new AccountCreateResponse();
        accountCreateResponse.setId(accountEntity.getId());
        accountCreateResponse.setName(accountEntity.getName());
        accountCreateResponse.setBalance(accountEntity.getMoney().toString());

        System.out.println(accountCreateRequest.toString());
        System.out.println(accountEntity.toString());
        System.out.println(accountCreateResponse.toString());

        return new Gson().toJson(accountCreateResponse);
    }

}
