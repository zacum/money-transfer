package com.moneytransfer.models.transaction;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class ReceivablesCreateRequestTest {

    @Test
    public void testReceivablesCreateRequestDefaultConstructor() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        ReceivablesCreateRequest receivablesCreateRequest = new ReceivablesCreateRequest();
        receivablesCreateRequest.setAccountId(accountId);
        receivablesCreateRequest.setAmount(accountAmount);
        receivablesCreateRequest.setCurrency(accountCurrency);

        assertEquals(accountId, receivablesCreateRequest.getAccountId());
        assertEquals(accountAmount, receivablesCreateRequest.getAmount());
        assertEquals(accountCurrency, receivablesCreateRequest.getCurrency());
    }

    @Test
    public void testReceivablesCreateRequestTransfersConstructor() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        TransfersCreateRequest transfersCreateRequest = new TransfersCreateRequest();
        transfersCreateRequest.setToAccountId(accountId);
        transfersCreateRequest.setAmount(accountAmount);
        transfersCreateRequest.setCurrency(accountCurrency);

        ReceivablesCreateRequest receivablesCreateRequest = new ReceivablesCreateRequest(transfersCreateRequest);

        assertEquals(accountId, receivablesCreateRequest.getAccountId());
        assertEquals(accountAmount, receivablesCreateRequest.getAmount());
        assertEquals(accountCurrency, receivablesCreateRequest.getCurrency());
    }

}
