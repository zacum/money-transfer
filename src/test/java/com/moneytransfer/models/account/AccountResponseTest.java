package com.moneytransfer.models.account;

import com.moneytransfer.entities.Account;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class AccountResponseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testAccountResponseConstructor() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setName(accountName);
        accountSaved.setAmount(accountAmount);
        accountSaved.setCurrency(accountCurrency);

        AccountResponse accountCreateRequest = new AccountResponse(accountSaved);

        assertEquals(1L, (long) accountCreateRequest.getId());
        assertEquals(accountName, accountCreateRequest.getName());
        assertEquals(accountAmount, accountCreateRequest.getAmount());
        assertEquals(accountCurrency, accountCreateRequest.getCurrency());
    }

    @Test
    public void testCreateAccountIncorrectCurrency() {
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "BAK";

        Account accountSaved = new Account();
        accountSaved.setId(1L);
        accountSaved.setName(accountName);
        accountSaved.setAmount(accountAmount);
        accountSaved.setCurrency(accountCurrency);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        new AccountResponse(accountSaved);
    }

}
