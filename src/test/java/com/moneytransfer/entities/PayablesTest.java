package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class PayablesTest {

    @Test
    public void testPayablesDefaultConstructor() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setId(id);
        payables.setAccountId(accountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        assertEquals(id, payables.getId());
        assertEquals(accountId, payables.getAccountId());
        assertEquals(amount, payables.getAmount());
        assertEquals(currency, payables.getCurrency());
    }

    @Test
    public void testPayablesConstructorWithMoney() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setId(id);
        payables.setAccountId(accountId);
        payables.setMoney(Money.of(amount, currency));

        assertEquals(id, payables.getId());
        assertEquals(accountId, payables.getAccountId());
        assertEquals(amount, payables.getAmount());
        assertEquals(currency, payables.getCurrency());
    }

}
