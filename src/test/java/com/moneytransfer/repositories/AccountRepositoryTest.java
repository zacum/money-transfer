package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.moneytransfer.DatabaseUtils;
import com.moneytransfer.ResourcesUtils;
import com.moneytransfer.entities.Account;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryTest {

    private static AccountRepository accountRepository;

    static {
        try {
            Database database = DatabaseUtils.getDatabase();
            database.sql(ResourcesUtils.getOriginalString("./src/main/resources/schema.sql")).execute();

            accountRepository = new AccountRepository();
            accountRepository.setDatabase(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        assertEquals(3, accounts.size());

        Account accountListed = accounts.get(accounts.size() - 1);

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
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
        Optional<Account> accountOpt = accountRepository.get(100L);

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
        Optional<Account> accountListedOpt = accountRepository.get(accountSaved.getId(), transaction);
        transaction.commit();

        assertTrue(accountListedOpt.isPresent());

        Account accountListed = accountListedOpt.get();

        assertEquals(accountSaved.getId(), accountListed.getId());
        assertEquals(accountSaved.getName(), accountListed.getName());
        assertEquals(accountSaved.getAmount().doubleValue(), accountListed.getAmount().doubleValue(), 0.0);
        assertEquals(accountSaved.getCurrency(), accountListed.getCurrency());
    }

    @Test
    public void testGetNonExistingTransactionAccount() {
        Transaction transaction = accountRepository.getTransaction();
        Optional<Account> accountListedOpt = accountRepository.get(100L, transaction);
        transaction.commit();

        assertTrue(accountListedOpt.isEmpty());
    }

}
