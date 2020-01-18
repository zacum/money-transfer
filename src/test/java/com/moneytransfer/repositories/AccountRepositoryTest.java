package com.moneytransfer.repositories;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.GuiceConfigurationAccountTest;
import com.moneytransfer.entities.Account;
import com.moneytransfer.exceptions.AccountNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountRepositoryTest {

    private Injector injector = Guice.createInjector(new GuiceConfigurationAccountTest());

    private AccountRepository accountRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        accountRepository = injector.getInstance(AccountRepository.class);
    }

    @After
    public void tearDown() {
        accountRepository.close();
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
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
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
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetNonExistingAccount() {
        Optional<Account> accountOpt = accountRepository.get(1L);

        assertTrue(accountOpt.isEmpty());
    }

    @Test
    public void testUpdateAccount() {
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        BigDecimal amountUpdated = BigDecimal.valueOf(20.70);
        String currency = "EUR";

        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        Account accountSaved = accountRepository.save(account);

        accountSaved.setAmount(amountUpdated);

        Transaction transaction = accountRepository.getTransaction();
        accountRepository.update(accountSaved, transaction);
        transaction.commit();

        Optional<Account> accountOpt = accountRepository.get(accountSaved.getId());

        assertTrue(accountOpt.isPresent());

        Account accountListed = accountOpt.get();

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetExistingTransactionAccount() {
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        Account accountSaved = accountRepository.save(account);

        Transaction transaction = accountRepository.getTransaction();
        Account accountListed = accountRepository.getAccountOrThrowNotFound(accountSaved.getId(), transaction);
        transaction.commit();

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetNonExistingThrowExceptionAccount() {
        Transaction transaction = accountRepository.getTransaction();
        try {
            accountRepository.getAccountOrThrowNotFound(1L, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            exceptionRule.expect(AccountNotFoundException.class);
            exceptionRule.expectMessage("Account is not found");
            throw t;
        }

        fail("Should throw exception");
    }

}
