package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.exceptions.AccountBalanceException;
import com.moneytransfer.exceptions.AccountNotFoundException;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;
import org.javamoney.moneta.Money;

import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {

    @Inject
    private AccountRepository accountRepository;

    public AccountResponse createAccount(AccountCreateRequest accountCreateRequest) {
        Account account = new Account(accountCreateRequest);
        return new AccountResponse(accountRepository.save(account));
    }

    public AccountResponse deposit(Receivables receivables, Transaction transaction) {
        Account account = getAccountOrThrowNotFound(receivables.getAccountId(), transaction);
        Money modification = getModification(account, receivables.getAmount(), receivables.getCurrency());
        Money original = Money.of(account.getAmount(), account.getCurrency());
        account.setMoney(original.add(modification));
        return new AccountResponse(accountRepository.update(account, transaction));
    }

    public AccountResponse withdraw(Payables payables, Transaction transaction) {
        Account account = getAccountOrThrowNotFound(payables.getAccountId(), transaction);
        Money modification = getModification(account, payables.getAmount(), payables.getCurrency());
        Money original = Money.of(account.getAmount(), account.getCurrency());
        account.setMoney(original.subtract(modification));
        if (account.getAmount().signum() < 0) {
            throw new AccountBalanceException("Paying account does not have sufficient funds");
        }
        return new AccountResponse(accountRepository.update(account, transaction));
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

    private Account getAccountOrThrowNotFound(Long accountId, Transaction transaction) {
        Optional<Account> accountOpt = accountRepository.get(accountId, transaction);
        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException("Account is not found");
        }
        return accountOpt.get();
    }

    private Money getModification(Account account, BigDecimal amount, String currency) {
        Money modification = Money.of(amount, currency);
        if (!currency.equals(account.getCurrency())) {
            return modification.with(MonetaryConversions.getConversion(account.getCurrency()));
        }
        return modification;
    }

}
