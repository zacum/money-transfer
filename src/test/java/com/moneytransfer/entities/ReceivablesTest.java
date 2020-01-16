package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class ReceivablesTest {

    @Test
    public void testPayablesDefaultConstructor() {
        Long payablesAccountId = 2L;
        BigDecimal payablesAmount = BigDecimal.valueOf(10.50);
        String payablesCurrency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(1L);
        receivables.setAccountId(payablesAccountId);
        receivables.setAmount(payablesAmount);
        receivables.setCurrency(payablesCurrency);

        assertEquals(1, (long) receivables.getId());
        assertEquals(payablesAccountId, receivables.getAccountId());
        assertEquals(payablesAmount, receivables.getAmount());
        assertEquals(payablesCurrency, receivables.getCurrency());
    }

    @Test
    public void testPayablesConstructorWithMoney() {
        Long payablesAccountId = 2L;
        BigDecimal payablesAmount = BigDecimal.valueOf(10.50);
        String payablesCurrency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(1L);
        receivables.setAccountId(payablesAccountId);
        receivables.setMoney(Money.of(payablesAmount, payablesCurrency));

        assertEquals(1, (long) receivables.getId());
        assertEquals(payablesAccountId, receivables.getAccountId());
        assertEquals(payablesAmount, receivables.getAmount());
        assertEquals(payablesCurrency, receivables.getCurrency());
    }

}
