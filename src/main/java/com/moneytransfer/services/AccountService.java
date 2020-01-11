package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public AccountResponse createAccount(AccountCreateRequest accountCreateRequest) {
        Account account = accountRepository.save(accountCreateRequest);
        return getAccountCreateResponse(account);
    }

    public Optional<AccountResponse> getAccount(Long accountId) {
        Optional<Account> accountOpt = accountRepository.get(accountId);
        if (accountOpt.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getAccountCreateResponse(accountOpt.get()));
    }

    private AccountResponse getAccountCreateResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setName(account.getName());
        accountResponse.setAmount(BigDecimal.valueOf(account.getMoney().getNumber().doubleValueExact()));
        accountResponse.setCurrency(account.getMoney().getCurrency().getCurrencyCode());
        return accountResponse;
    }

}
