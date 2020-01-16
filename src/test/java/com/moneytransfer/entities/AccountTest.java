package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class AccountTest {

    @Test
    public void testAccountDefaultConstructor() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(1L);
        account.setName(accountName);
        account.setAmount(accountAmount);
        account.setCurrency(accountCurrency);

        assertEquals(1, (long) account.getId());
        assertEquals(accountName, account.getName());
        assertEquals(accountAmount, account.getAmount());
        assertEquals(accountCurrency, account.getCurrency());
    }

    @Test
    public void testAccountConstructorWithMoney() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account account = new Account();
        account.setId(1L);
        account.setName(accountName);
        account.setMoney(Money.of(accountAmount, accountCurrency));

        assertEquals(1, (long) account.getId());
        assertEquals(accountName, account.getName());
        assertEquals(accountAmount, account.getAmount());
        assertEquals(accountCurrency, account.getCurrency());
    }

}
