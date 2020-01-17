package com.moneytransfer.models.transaction;

import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class TransactionResponseTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testTransactionResponseDefaultConstructor() {
        Long id = 1L;
        Long fromAccountId = 2L;
        Long toAccountId = 3L;
        Long payablesId = 4L;
        Long receivablesId = 5L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(id);
        transactionResponse.setFromAccountId(fromAccountId);
        transactionResponse.setToAccountId(toAccountId);
        transactionResponse.setPayablesId(payablesId);
        transactionResponse.setReceivablesId(receivablesId);
        transactionResponse.setAmount(accountAmount);
        transactionResponse.setCurrency(accountCurrency);

        assertEquals(id, transactionResponse.getId());
        assertEquals(fromAccountId, transactionResponse.getFromAccountId());
        assertEquals(toAccountId, transactionResponse.getToAccountId());
        assertEquals(payablesId, transactionResponse.getPayablesId());
        assertEquals(receivablesId, transactionResponse.getReceivablesId());
        assertEquals(accountAmount, transactionResponse.getAmount());
        assertEquals(accountCurrency, transactionResponse.getCurrency());
    }

    @Test
    public void testTransactionResponsePayablesConstructor() {
        Long fromAccountId = 2L;
        Long payablesId = 4L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Payables payables = new Payables();
        payables.setId(payablesId);
        payables.setAccountId(fromAccountId);
        payables.setAmount(accountAmount);
        payables.setCurrency(accountCurrency);

        TransactionResponse transactionResponse = new TransactionResponse(payables);

        assertEquals(payablesId, transactionResponse.getId());
        assertEquals(fromAccountId, transactionResponse.getFromAccountId());
        assertNull(transactionResponse.getToAccountId());
        assertNull(transactionResponse.getPayablesId());
        assertNull(transactionResponse.getReceivablesId());
        assertEquals(accountAmount, transactionResponse.getAmount());
        assertEquals(accountCurrency, transactionResponse.getCurrency());
    }

    @Test
    public void testTransactionResponsePayablesIncorrectCurrency() {
        Long fromAccountId = 2L;
        Long payablesId = 4L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "BAK";

        Payables payables = new Payables();
        payables.setId(payablesId);
        payables.setAccountId(fromAccountId);
        payables.setAmount(accountAmount);
        payables.setCurrency(accountCurrency);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        new TransactionResponse(payables);
    }

    @Test
    public void testTransactionResponseReceivablesConstructor() {
        Long toAccountId = 3L;
        Long receivablesId = 5L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(receivablesId);
        receivables.setAccountId(toAccountId);
        receivables.setAmount(accountAmount);
        receivables.setCurrency(accountCurrency);

        TransactionResponse transactionResponse = new TransactionResponse(receivables);

        assertEquals(receivablesId, transactionResponse.getId());
        assertNull(transactionResponse.getFromAccountId());
        assertEquals(toAccountId, transactionResponse.getToAccountId());
        assertNull(transactionResponse.getPayablesId());
        assertNull(transactionResponse.getReceivablesId());
        assertEquals(accountAmount, transactionResponse.getAmount());
        assertEquals(accountCurrency, transactionResponse.getCurrency());
    }

    @Test
    public void testTransactionResponseReceivablesIncorrectCurrency() {
        Long toAccountId = 3L;
        Long receivablesId = 5L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "BAK";

        Receivables receivables = new Receivables();
        receivables.setId(receivablesId);
        receivables.setAccountId(toAccountId);
        receivables.setAmount(accountAmount);
        receivables.setCurrency(accountCurrency);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        new TransactionResponse(receivables);
    }

    @Test
    public void testTransactionResponseTransfersConstructor() {
        Long fromAccountId = 2L;
        Long toAccountId = 3L;
        Long payablesId = 4L;
        Long receivablesId = 5L;
        Long transfersId = 6L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Payables payables = new Payables();
        payables.setId(payablesId);
        payables.setAccountId(fromAccountId);
        payables.setAmount(accountAmount);
        payables.setCurrency(accountCurrency);

        Receivables receivables = new Receivables();
        receivables.setId(receivablesId);
        receivables.setAccountId(toAccountId);
        receivables.setAmount(accountAmount);
        receivables.setCurrency(accountCurrency);

        Transfers transfers = new Transfers();
        transfers.setId(transfersId);
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        TransactionResponse transactionResponse = new TransactionResponse(payables, receivables, transfers);

        assertEquals(transfersId, transactionResponse.getId());
        assertEquals(fromAccountId, transactionResponse.getFromAccountId());
        assertEquals(toAccountId, transactionResponse.getToAccountId());
        assertEquals(payablesId, transactionResponse.getPayablesId());
        assertEquals(receivablesId, transactionResponse.getReceivablesId());
        assertEquals(accountAmount, transactionResponse.getAmount());
        assertEquals(accountCurrency, transactionResponse.getCurrency());
    }

    @Test
    public void testTransactionResponseTransfersIncorrectCurrency() {
        Long fromAccountId = 2L;
        Long toAccountId = 3L;
        Long payablesId = 4L;
        Long receivablesId = 5L;
        Long transfersId = 6L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "BAK";

        Payables payables = new Payables();
        payables.setId(payablesId);
        payables.setAccountId(fromAccountId);
        payables.setAmount(accountAmount);
        payables.setCurrency(accountCurrency);

        Receivables receivables = new Receivables();
        receivables.setId(receivablesId);
        receivables.setAccountId(toAccountId);
        receivables.setAmount(accountAmount);
        receivables.setCurrency(accountCurrency);

        Transfers transfers = new Transfers();
        transfers.setId(transfersId);
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        new TransactionResponse(payables, receivables, transfers);
    }

}
