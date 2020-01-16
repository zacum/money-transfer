package com.moneytransfer.models.account;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AccountCreateRequestTest {

    @Test
    public void testAccountCreateRequestConstructor() {
        String accountName = "Victor Account";
        String accountCurrency = "EUR";

        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();
        accountCreateRequest.setName(accountName);
        accountCreateRequest.setCurrency(accountCurrency);

        assertEquals(accountName, accountCreateRequest.getName());
        assertEquals(accountCurrency, accountCreateRequest.getCurrency());
    }
}
