package com.moneytransfer.models.transaction;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class TransfersCreateRequestTest {

    @Test
    public void testReceivablesCreateRequestDefaultConstructor() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        TransfersCreateRequest transfersCreateRequest = new TransfersCreateRequest();
        transfersCreateRequest.setFromAccountId(fromAccountId);
        transfersCreateRequest.setToAccountId(toAccountId);
        transfersCreateRequest.setAmount(accountAmount);
        transfersCreateRequest.setCurrency(accountCurrency);

        assertEquals(fromAccountId, transfersCreateRequest.getFromAccountId());
        assertEquals(toAccountId, transfersCreateRequest.getToAccountId());
        assertEquals(accountAmount, transfersCreateRequest.getAmount());
        assertEquals(accountCurrency, transfersCreateRequest.getCurrency());
    }

}
