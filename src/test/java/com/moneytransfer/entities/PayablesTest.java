package com.moneytransfer.entities;

import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class PayablesTest {

    @Test
    public void testPayablesDefaultConstructor() {
        Long payablesAccountId = 2L;
        BigDecimal payablesAmount = BigDecimal.valueOf(10.50);
        String payablesCurrency = "EUR";

        Payables payables = new Payables();
        payables.setId(1L);
        payables.setAccountId(payablesAccountId);
        payables.setAmount(payablesAmount);
        payables.setCurrency(payablesCurrency);

        assertEquals(1, (long) payables.getId());
        assertEquals(payablesAccountId, payables.getAccountId());
        assertEquals(payablesAmount, payables.getAmount());
        assertEquals(payablesCurrency, payables.getCurrency());
    }

    @Test
    public void testPayablesConstructorWithMoney() {
        Long payablesAccountId = 2L;
        BigDecimal payablesAmount = BigDecimal.valueOf(10.50);
        String payablesCurrency = "EUR";

        Payables payables = new Payables();
        payables.setId(1L);
        payables.setAccountId(payablesAccountId);
        payables.setMoney(Money.of(payablesAmount, payablesCurrency));

        assertEquals(1, (long) payables.getId());
        assertEquals(payablesAccountId, payables.getAccountId());
        assertEquals(payablesAmount, payables.getAmount());
        assertEquals(payablesCurrency, payables.getCurrency());
    }

}
