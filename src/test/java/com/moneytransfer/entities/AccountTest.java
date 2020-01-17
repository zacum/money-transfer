package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class AccountTest {

    @Test
    public void testAccountDefaultConstructor() {
        Long id = 1L;
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setAmount(amount);
        account.setCurrency(currency);

        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(amount, account.getAmount());
        assertEquals(currency, account.getCurrency());
    }

    @Test
    public void testAccountConstructorWithMoney() {
        Long id = 1L;
        String name = "Victor Account";
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setMoney(Money.of(amount, currency));

        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(amount, account.getAmount());
        assertEquals(currency, account.getCurrency());
    }

}
