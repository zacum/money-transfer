package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class ReceivablesTest {

    @Test
    public void testReceivablesDefaultConstructor() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(id);
        receivables.setAccountId(accountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        assertEquals(id, receivables.getId());
        assertEquals(accountId, receivables.getAccountId());
        assertEquals(amount, receivables.getAmount());
        assertEquals(currency, receivables.getCurrency());
    }

    @Test
    public void testReceivablesConstructorWithMoney() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(id);
        receivables.setAccountId(accountId);
        receivables.setMoney(Money.of(amount, currency));

        assertEquals(id, receivables.getId());
        assertEquals(accountId, receivables.getAccountId());
        assertEquals(amount, receivables.getAmount());
        assertEquals(currency, receivables.getCurrency());
    }

}
