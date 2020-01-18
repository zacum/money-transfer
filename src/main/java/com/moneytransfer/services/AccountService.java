package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.exceptions.InsufficientAccountBalanceException;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;
import org.javamoney.moneta.Money;

import javax.money.convert.MonetaryConversions;
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

    public Account deposit(Receivables receivables, Transaction transaction) {
        Account account = accountRepository.getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(receivables.getAmount(), receivables.getCurrency());
        if (!receivables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.add(modification));
        return account;
    }

    public Account withdraw(Payables payables, Transaction transaction) {
        Account account = accountRepository.getAccountOrThrowNotFound(payables.getAccountId(), transaction);
        Money original = Money.of(account.getAmount(), account.getCurrency());
        Money modification = Money.of(payables.getAmount(), payables.getCurrency());
        if (!payables.getCurrency().equals(account.getCurrency())) {
            modification = modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        account.setMoney(original.subtract(modification));
        if (account.getAmount().signum() < 0) {
            throw new InsufficientAccountBalanceException("Paying account does not have sufficient funds");
        }
        return account;
    }

    public AccountResponse update(Account account, Transaction transaction) {
        return new AccountResponse(accountRepository.update(account,transaction));
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository.getAccounts().stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    public Optional<AccountResponse> getAccount(Long accountId) {
        return accountRepository.get(accountId)
                .map(AccountResponse::new);
    }

}
