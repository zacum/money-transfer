package com.moneytransfer.repositories;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.GuiceConfiguration;
import com.moneytransfer.entities.Account;
import com.moneytransfer.repositories.AccountRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryTest {

    private Injector injector = Guice.createInjector(new GuiceConfiguration());

    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        accountRepository = injector.getInstance(AccountRepository.class);
    }

    @Test
    public void testSaveAccount() {
        Long id = 1L;
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        Account accountSaved = accountRepository.save(account);

        assertEquals(id, accountSaved.getId());
        assertEquals(name, accountSaved.getName());
        assertEquals(amount, accountSaved.getAmount());
        assertEquals(currency, accountSaved.getCurrency());
    }

    @Test
    public void testGetAccountsNonEmpty() {
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        Account accountSaved = accountRepository.save(account);

        List<Account> accounts = accountRepository.getAccounts();

        assertEquals(1, accounts.size());

        Account accountListed = accounts.get(0);

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount(), accountListed.getAmount());
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetAccountsEmpty() {
        List<Account> accounts = accountRepository.getAccounts();

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testGetExistingAccount() {
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        Account accountSaved = accountRepository.save(account);

        Optional<Account> accountOpt = accountRepository.get(accountSaved.getId());

        assertTrue(accountOpt.isPresent());

        Account accountListed = accountOpt.get();

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount(), accountListed.getAmount());
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetNonExistingAccount() {
        Optional<Account> accountOpt = accountRepository.get(1L);

        assertTrue(accountOpt.isEmpty());
    }

}
