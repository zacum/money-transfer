package com.moneytransfer.services;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountCreateResponse;
import com.moneytransfer.repositories.AccountRepository;

import java.math.BigDecimal;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public String createAccount(AccountCreateRequest accountCreateRequest) {
        Account account = accountRepository.save(accountCreateRequest);

        AccountCreateResponse accountCreateResponse = getAccountCreateResponse(account);

        System.out.println(accountCreateRequest.toString());
        System.out.println(account.toString());
        System.out.println(accountCreateResponse.toString());

        return new Gson().toJson(accountCreateResponse);
    }

    public String getAccount(Long accountId) {
        Account account = accountRepository.get(accountId);
        AccountCreateResponse accountCreateResponse = getAccountCreateResponse(account);
        return new Gson().toJson(accountCreateResponse);
    }

    private AccountCreateResponse getAccountCreateResponse(Account account) {
        AccountCreateResponse accountCreateResponse = new AccountCreateResponse();
        accountCreateResponse.setId(account.getId());
        accountCreateResponse.setName(account.getName());
        accountCreateResponse.setAmount(BigDecimal.valueOf(account.getMoney().getNumber().doubleValueExact()));
        accountCreateResponse.setCurrency(account.getMoney().getCurrency().getCurrencyCode());
        return accountCreateResponse;
    }

}
