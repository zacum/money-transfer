package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.moneytransfer.MockTransaction;
import com.moneytransfer.entities.Account;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.exceptions.InsufficientAccountBalanceException;
import com.moneytransfer.models.account.AccountCreateRequest;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.repositories.AccountRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreateAccountSuccessfully() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(0.0);
        String accountCurrency = "EUR";

        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();
        accountCreateRequest.setName(accountName);
        accountCreateRequest.setCurrency(accountCurrency);

        Account accountRequested = new Account();
        accountRequested.setName(accountName);
        accountRequested.setAmount(accountAmount);
        accountRequested.setCurrency(accountCurrency);

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setName(accountName);
        accountSaved.setAmount(accountAmount);
        accountSaved.setCurrency(accountCurrency);

        when(accountRepository.save(eq(accountRequested))).thenReturn(accountSaved);

        AccountResponse accountResponse = accountService.createAccount(accountCreateRequest);

        assertEquals(1, (long) accountResponse.getId());
        assertEquals(accountName, accountResponse.getName());
        assertEquals(accountAmount, accountResponse.getAmount());
        assertEquals(accountCurrency, accountResponse.getCurrency());

        verify(accountRepository).save(eq(accountRequested));
    }

    @Test
    public void testDepositToAccountSameCurrency() {
        Long accountId = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(accountId);
        account.setName(accountName);
        account.setAmount(accountAmount);
        account.setCurrency(accountCurrency);

        Receivables receivables = new Receivables();
        receivables.setId(1L);
        receivables.setAccountId(accountId);
        receivables.setAmount(BigDecimal.valueOf(10.50));
        receivables.setCurrency(accountCurrency);

        when(accountRepository.getAccountOrThrowNotFound(eq(accountId), any(Transaction.class))).thenReturn(account);

        Account accountUpdated = accountService.deposit(receivables, new MockTransaction());

        assertEquals(accountId, accountUpdated.getId());
        assertEquals(accountName, accountUpdated.getName());
        assertEquals(BigDecimal.valueOf(21.00), accountUpdated.getAmount());
        assertEquals(accountCurrency, accountUpdated.getCurrency());

        verify(accountRepository).getAccountOrThrowNotFound(eq(accountId), any(Transaction.class));
    }

    @Test
    public void testWithdrawFromAccountSameCurrencySuccessfully() {
        Long accountId = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(21.00);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(accountId);
        account.setName(accountName);
        account.setAmount(accountAmount);
        account.setCurrency(accountCurrency);

        Payables payables = new Payables();
        payables.setId(1L);
        payables.setAccountId(accountId);
        payables.setAmount(BigDecimal.valueOf(10.50));
        payables.setCurrency(accountCurrency);

        when(accountRepository.getAccountOrThrowNotFound(eq(accountId), any(Transaction.class))).thenReturn(account);

        Account accountUpdated = accountService.withdraw(payables, new MockTransaction());

        assertEquals(accountId, accountUpdated.getId());
        assertEquals(accountName, accountUpdated.getName());
        assertEquals(BigDecimal.valueOf(10.50), accountUpdated.getAmount());
        assertEquals(accountCurrency, accountUpdated.getCurrency());

        verify(accountRepository).getAccountOrThrowNotFound(eq(accountId), any(Transaction.class));
    }

    @Test
    public void testWithdrawFromAccountSameCurrencyZeroBalanceSuccessfully() {
        Long accountId = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(21.00);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(accountId);
        account.setName(accountName);
        account.setAmount(accountAmount);
        account.setCurrency(accountCurrency);

        Payables payables = new Payables();
        payables.setId(1L);
        payables.setAccountId(accountId);
        payables.setAmount(BigDecimal.valueOf(21.00));
        payables.setCurrency(accountCurrency);

        when(accountRepository.getAccountOrThrowNotFound(eq(accountId), any(Transaction.class))).thenReturn(account);

        Account accountUpdated = accountService.withdraw(payables, new MockTransaction());

        assertEquals(accountId, accountUpdated.getId());
        assertEquals(accountName, accountUpdated.getName());
        assertEquals(BigDecimal.valueOf(0.0), accountUpdated.getAmount());
        assertEquals(accountCurrency, accountUpdated.getCurrency());

        verify(accountRepository).getAccountOrThrowNotFound(eq(accountId), any(Transaction.class));
    }

    @Test
    public void testWithdrawFromAccountSameCurrencyInsufficientFunds() {
        Long accountId = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(21.00);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(accountId);
        account.setName(accountName);
        account.setAmount(accountAmount);
        account.setCurrency(accountCurrency);

        Payables payables = new Payables();
        payables.setId(1L);
        payables.setAccountId(accountId);
        payables.setAmount(BigDecimal.valueOf(21.01));
        payables.setCurrency(accountCurrency);

        when(accountRepository.getAccountOrThrowNotFound(eq(accountId), any(Transaction.class))).thenReturn(account);

        exceptionRule.expect(InsufficientAccountBalanceException.class);
        exceptionRule.expectMessage("Paying account does not have sufficient funds");

        accountService.withdraw(payables, new MockTransaction());
    }

    @Test
    public void testUpdateAccount() {
        Long id = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(0.0);
        String accountCurrency = "EUR";

        Account accountUpdated = new Account();
        accountUpdated.setId(id);
        accountUpdated.setName(accountName);
        accountUpdated.setAmount(accountAmount);
        accountUpdated.setCurrency(accountCurrency);

        when(accountRepository.update(eq(accountUpdated), any(Transaction.class))).thenReturn(accountUpdated);

        AccountResponse accountResponse = accountService.update(accountUpdated, new MockTransaction());

        assertEquals(1, (long) accountResponse.getId());
        assertEquals(accountName, accountResponse.getName());
        assertEquals(accountAmount, accountResponse.getAmount());
        assertEquals(accountCurrency, accountResponse.getCurrency());

        verify(accountRepository).update(eq(accountUpdated), any(Transaction.class));
    }

    @Test
    public void testListAccounts() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setName(accountName);
        accountSaved.setAmount(accountAmount);
        accountSaved.setCurrency(accountCurrency);

        when(accountRepository.getAccounts()).thenReturn(Collections.singletonList(accountSaved));

        List<AccountResponse> accounts = accountService.getAccounts();

        assertEquals(1, accounts.size());

        AccountResponse accountResponse = accounts.get(0);

        assertEquals(1, (long) accountResponse.getId());
        assertEquals(accountName, accountResponse.getName());
        assertEquals(accountAmount, accountResponse.getAmount());
        assertEquals(accountCurrency, accountResponse.getCurrency());

        verify(accountRepository).getAccounts();
    }

    @Test
    public void testGetAccountSuccessfully() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setName(accountName);
        accountSaved.setAmount(accountAmount);
        accountSaved.setCurrency(accountCurrency);

        when(accountRepository.get(eq(1L))).thenReturn(Optional.of(accountSaved));

        Optional<AccountResponse> accountOpt = accountService.getAccount(1L);

        assertTrue(accountOpt.isPresent());

        AccountResponse accountResponse = accountOpt.get();

        assertEquals(1, (long) accountResponse.getId());
        assertEquals(accountName, accountResponse.getName());
        assertEquals(accountAmount, accountResponse.getAmount());
        assertEquals(accountCurrency, accountResponse.getCurrency());

        verify(accountRepository).get(eq(1L));
    }

    @Test
    public void testGetAccountNotFound() {
        when(accountRepository.get(eq(1L))).thenReturn(Optional.empty());

        Optional<AccountResponse> accountOpt = accountService.getAccount(1L);

        assertTrue(accountOpt.isEmpty());

        verify(accountRepository).get(eq(1L));
    }

}
