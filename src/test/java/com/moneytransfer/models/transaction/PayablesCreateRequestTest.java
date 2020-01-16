package com.moneytransfer.models.transaction;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class PayablesCreateRequestTest {

    @Test
    public void testPayablesCreateRequestDefaultConstructor() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        PayablesCreateRequest payablesCreateRequest = new PayablesCreateRequest();
        payablesCreateRequest.setAccountId(accountId);
        payablesCreateRequest.setAmount(accountAmount);
        payablesCreateRequest.setCurrency(accountCurrency);

        assertEquals(accountId, payablesCreateRequest.getAccountId());
        assertEquals(accountAmount, payablesCreateRequest.getAmount());
        assertEquals(accountCurrency, payablesCreateRequest.getCurrency());
    }

    @Test
    public void testPayablesCreateRequestTransfersDefaultConstructor() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        TransfersCreateRequest transfersCreateRequest = new TransfersCreateRequest();
        transfersCreateRequest.setFromAccountId(accountId);
        transfersCreateRequest.setAmount(accountAmount);
        transfersCreateRequest.setCurrency(accountCurrency);

        PayablesCreateRequest payablesCreateRequest = new PayablesCreateRequest(transfersCreateRequest);

        assertEquals(accountId, payablesCreateRequest.getAccountId());
        assertEquals(accountAmount, payablesCreateRequest.getAmount());
        assertEquals(accountCurrency, payablesCreateRequest.getCurrency());
    }

}
