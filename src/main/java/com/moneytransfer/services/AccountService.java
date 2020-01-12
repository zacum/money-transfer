package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;
import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public AccountResponse createAccount(AccountCreateRequest accountCreateRequest) {
        Account account = new Account();
        account.setName(accountCreateRequest.getName());
        account.setMoney(Money.of(0, accountCreateRequest.getCurrency()));
        return new AccountResponse(accountRepository.save(account));
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository.getAccounts().stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    public Optional<AccountResponse> getAccount(Long accountId) {
        List<Account> accounts = accountRepository.get(accountId);
        if (accounts.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(new AccountResponse(accounts.get(0)));
    }

}
