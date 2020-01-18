package com.moneytransfer.entities;

import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void testReceivablesCreateRequestConstructor() {
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        ReceivablesCreateRequest receivablesCreateRequest = new ReceivablesCreateRequest();
        receivablesCreateRequest.setAccountId(accountId);
        receivablesCreateRequest.setAmount(amount);
        receivablesCreateRequest.setCurrency(currency);

        Receivables receivables = new Receivables(receivablesCreateRequest);

        assertNull(receivables.getId());
        assertEquals(accountId, receivables.getAccountId());
        assertEquals(amount, receivables.getAmount());
        assertEquals(currency, receivables.getCurrency());
    }

}
