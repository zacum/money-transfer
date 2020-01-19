package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.moneytransfer.MockTransaction;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.AccountBalanceException;
import com.moneytransfer.models.account.AccountResponse;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransactionResponse;
import com.moneytransfer.repositories.TransactionRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        when(transactionRepository.getTransaction()).thenReturn(new MockTransaction());
    }

    @Test
    public void testCreatePayablesSuccessfully() {
        Long accountId = 1L;
        String accountName = "Victor Account";
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        Long payablesId = 2L;
        BigDecimal payablesAmount = BigDecimal.valueOf(5.50);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(accountId);
        accountResponse.setName(accountName);
        accountResponse.setAmount(accountAmount);
        accountResponse.setCurrency(accountCurrency);

        Payables payablesSaved = new Payables();
        payablesSaved.setId(payablesId);
        payablesSaved.setAccountId(accountId);
        payablesSaved.setAmount(payablesAmount);
        payablesSaved.setCurrency(accountCurrency);

        PayablesCreateRequest payablesCreateRequest = new PayablesCreateRequest();
        payablesCreateRequest.setAccountId(accountId);
        payablesCreateRequest.setAmount(accountAmount);
        payablesCreateRequest.setCurrency(accountCurrency);

        when(accountService.withdraw(any(Payables.class), any(Transaction.class))).thenReturn(accountResponse);
        when(transactionRepository.save(any(Payables.class), any(Transaction.class))).thenReturn(payablesSaved);

        TransactionResponse transactionResponse = transactionService.createPayables(payablesCreateRequest);

        assertEquals(payablesId, transactionResponse.getId());
        assertEquals(accountId, transactionResponse.getFromAccountId());
        assertNull(transactionResponse.getToAccountId());
        assertNull(transactionResponse.getPayablesId());
        assertNull(transactionResponse.getReceivablesId());
        assertEquals(payablesAmount, transactionResponse.getAmount());
        assertEquals(accountCurrency, transactionResponse.getCurrency());

        verify(accountService).withdraw(any(Payables.class), any(Transaction.class));
        verify(transactionRepository).save(any(Payables.class), any(Transaction.class));
    }

    @Test
    public void testCreatePayablesRollback() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "EUR";

        PayablesCreateRequest payablesCreateRequest = new PayablesCreateRequest();
        payablesCreateRequest.setAccountId(accountId);
        payablesCreateRequest.setAmount(accountAmount);
        payablesCreateRequest.setCurrency(accountCurrency);

        when(accountService.withdraw(any(Payables.class), any(Transaction.class))).thenThrow(new AccountBalanceException("Paying account does not have sufficient funds"));

        exceptionRule.expect(AccountBalanceException.class);
        exceptionRule.expectMessage("Paying account does not have sufficient funds");

        transactionService.createPayables(payablesCreateRequest);
    }

    @Test
    public void testCreateReceivablesRollback() {
        Long accountId = 1L;
        BigDecimal accountAmount = BigDecimal.valueOf(10.50);
        String accountCurrency = "BAK";

        ReceivablesCreateRequest receivablesCreateRequest = new ReceivablesCreateRequest();
        receivablesCreateRequest.setAccountId(accountId);
        receivablesCreateRequest.setAmount(accountAmount);
        receivablesCreateRequest.setCurrency(accountCurrency);

        exceptionRule.expect(UnknownCurrencyException.class);
        exceptionRule.expectMessage("Unknown currency code: BAK");

        transactionService.createReceivables(receivablesCreateRequest);
    }

    @Test
    public void testListPayables() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setId(id);
        payables.setAccountId(accountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        when(transactionRepository.getPayables()).thenReturn(Collections.singletonList(payables));

        List<TransactionResponse> payablesList = transactionService.getPayables();

        assertEquals(1, payablesList.size());

        TransactionResponse transactionResponse = payablesList.get(0);

        assertEquals(id, transactionResponse.getId());
        assertEquals(accountId, transactionResponse.getFromAccountId());
        assertEquals(amount, transactionResponse.getAmount());
        assertEquals(currency, transactionResponse.getCurrency());

        verify(transactionRepository).getPayables();
    }

    @Test
    public void testListReceivables() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setId(id);
        receivables.setAccountId(accountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        when(transactionRepository.getReceivables()).thenReturn(Collections.singletonList(receivables));

        List<TransactionResponse> receivablesList = transactionService.getReceivables();

        assertEquals(1, receivablesList.size());

        TransactionResponse transactionResponse = receivablesList.get(0);

        assertEquals(id, transactionResponse.getId());
        assertEquals(accountId, transactionResponse.getToAccountId());
        assertEquals(amount, transactionResponse.getAmount());
        assertEquals(currency, transactionResponse.getCurrency());

        verify(transactionRepository).getReceivables();
    }

    @Test
    public void testListTransfers() {
        Long payablesId = 2L;
        Long payablesAccountId = 4L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setId(payablesId);
        payables.setAccountId(payablesAccountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        when(transactionRepository.getPayables(eq(payablesId))).thenReturn(Optional.of(payables));

        Long receivablesId = 3L;
        Long receivablesAccountId = 5L;

        Receivables receivables = new Receivables();
        receivables.setId(receivablesId);
        receivables.setAccountId(receivablesAccountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        when(transactionRepository.getReceivables(eq(receivablesId))).thenReturn(Optional.of(receivables));

        Long transfersId = 1L;

        Transfers transfers = new Transfers();
        transfers.setId(transfersId);
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        when(transactionRepository.getTransfers()).thenReturn(Collections.singletonList(transfers));

        List<TransactionResponse> transfersList = transactionService.getTransfers();

        assertEquals(1, transfersList.size());

        TransactionResponse transactionResponse = transfersList.get(0);

        assertEquals(transfersId, transactionResponse.getId());
        assertEquals(payablesAccountId, transactionResponse.getFromAccountId());
        assertEquals(receivablesAccountId, transactionResponse.getToAccountId());
        assertEquals(payablesId, transactionResponse.getPayablesId());
        assertEquals(receivablesId, transactionResponse.getReceivablesId());
        assertEquals(amount, transactionResponse.getAmount());
        assertEquals(currency, transactionResponse.getCurrency());

        verify(transactionRepository).getPayables(eq(payablesId));
        verify(transactionRepository).getReceivables(eq(receivablesId));
        verify(transactionRepository).getTransfers();
    }

}
