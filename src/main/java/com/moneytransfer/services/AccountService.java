package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;
import org.javamoney.moneta.Money;

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
        Money money = Money.of(account.getAmount(), account.getCurrency());
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setName(account.getName());
        accountResponse.setAmount(BigDecimal.valueOf(money.getNumber().doubleValueExact()));
        accountResponse.setCurrency(money.getCurrency().getCurrencyCode());
        return accountResponse;
    }

}
