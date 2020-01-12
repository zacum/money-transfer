package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public Optional<AccountResponse> createAccount(AccountCreateRequest accountCreateRequest) {
        Optional<Account> accountOpt = accountRepository.save(accountCreateRequest);
        if (accountOpt.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new AccountResponse(accountOpt.get()));
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository.getAccounts().stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    public Optional<AccountResponse> getAccount(Long accountId) {
        Optional<Account> accountOpt = accountRepository.get(accountId);
        if (accountOpt.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new AccountResponse(accountOpt.get()));
    }

}
