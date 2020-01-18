package com.moneytransfer.entities;

import com.moneytransfer.models.account.AccountCreateRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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
    public void testAccountCreateRequestConstructor() {
        String name = "Victor Account";
        String currency = "EUR";

        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();
        accountCreateRequest.setName(name);
        accountCreateRequest.setCurrency(currency);

        Account account = new Account(accountCreateRequest);

        assertNull(account.getId());
        assertEquals(name, account.getName());
        assertEquals(0.0, account.getAmount().doubleValue(), 0.0);
        assertEquals(currency, account.getCurrency());
    }

    @Test
    public void testAccountCreateRequestConstructorIncorrectCurrency() {
        String name = "Victor Account";
        String currency = "BAK";

        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();
        accountCreateRequest.setName(name);
        accountCreateRequest.setCurrency(currency);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        new Account(accountCreateRequest);
    }

}
