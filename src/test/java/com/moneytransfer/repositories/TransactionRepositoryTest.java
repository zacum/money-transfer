package com.moneytransfer.repositories;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.moneytransfer.GuiceConfigurationTransactionTest;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

// FIXME: Norm seems to reconnect to H2 and drop the tables
@Ignore
public class TransactionRepositoryTest {

    private Injector injector = Guice.createInjector(new GuiceConfigurationTransactionTest());

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        accountRepository = injector.getInstance(AccountRepository.class);
        transactionRepository = injector.getInstance(TransactionRepository.class);
    }

    @After
    public void tearDown() {
        accountRepository.close();
        transactionRepository.close();
    }

    @Test
    public void testSavePayables() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setAccountId(accountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Payables payablesSaved = transactionRepository.save(payables, transaction);
        transaction.commit();

        assertEquals(id, payablesSaved.getId());
        assertEquals(accountId, payablesSaved.getAccountId());
        assertEquals(amount, payablesSaved.getAmount());
        assertEquals(currency, payablesSaved.getCurrency());
    }

    @Test
    public void testGetPayablesNonEmpty() {
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setAccountId(accountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Payables payablesSaved = transactionRepository.save(payables, transaction);
        transaction.commit();

        List<Payables> payablesList = transactionRepository.getPayables();

        assertEquals(1, payablesList.size());

        Payables payablesListed = payablesList.get(0);

        assertEquals(payablesSaved.getId(), payablesListed.getId());
        assertEquals(payablesSaved.getAccountId(), payablesListed.getAccountId());
        assertEquals(payablesSaved.getAmount().doubleValue(), payablesListed.getAmount().doubleValue(), 0.0);
        assertEquals(payablesSaved.getCurrency(), payablesListed.getCurrency());
    }

    @Test
    public void testGetPayablesEmpty() {
        List<Payables> payables = transactionRepository.getPayables();

        assertTrue(payables.isEmpty());
    }

    @Test
    public void testSaveReceivables() {
        Long id = 1L;
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setAccountId(accountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Receivables receivablesSaved = transactionRepository.save(receivables, transaction);
        transaction.commit();

        assertEquals(id, receivablesSaved.getId());
        assertEquals(accountId, receivablesSaved.getAccountId());
        assertEquals(amount, receivablesSaved.getAmount());
        assertEquals(currency, receivablesSaved.getCurrency());
    }

    @Test
    public void testGetReceivablesNonEmpty() {
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setAccountId(accountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Receivables receivablesSaved = transactionRepository.save(receivables, transaction);
        transaction.commit();

        List<Receivables> receivablesList = transactionRepository.getReceivables();

        assertEquals(1, receivablesList.size());

        Receivables receivablesListed = receivablesList.get(0);

        assertEquals(receivablesSaved.getId(), receivablesListed.getId());
        assertEquals(receivablesSaved.getAccountId(), receivablesListed.getAccountId());
        assertEquals(receivablesSaved.getAmount().doubleValue(), receivablesListed.getAmount().doubleValue(), 0.0);
        assertEquals(receivablesSaved.getCurrency(), receivablesListed.getCurrency());
    }

    @Test
    public void testGetReceivablesEmpty() {
        List<Receivables> receivables = transactionRepository.getReceivables();

        assertTrue(receivables.isEmpty());
    }

    @Test
    public void testSaveTransfers() {
        Long id = 1L;
        Long payablesId = 1L;
        Long receivablesId = 1L;

        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setAccountId(1L);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        Receivables receivables = new Receivables();
        receivables.setAccountId(2L);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        transactionRepository.save(payables, transaction);
        transactionRepository.save(receivables, transaction);

        Transfers transfers = new Transfers();
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        Transfers transfersSaved = transactionRepository.save(transfers, transaction);
        transaction.commit();

        assertEquals(id, transfers.getId());
        assertEquals(payablesId, transfersSaved.getPayablesId());
        assertEquals(receivablesId, transfersSaved.getReceivablesId());
    }

    @Test
    public void testGetTransfersNonEmpty() {
        Long payablesId = 1L;
        Long receivablesId = 1L;

        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setAccountId(1L);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        Receivables receivables = new Receivables();
        receivables.setAccountId(2L);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        transactionRepository.save(payables, transaction);
        transactionRepository.save(receivables, transaction);

        Transfers transfers = new Transfers();
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        Transfers transfersSaved = transactionRepository.save(transfers, transaction);
        transaction.commit();

        List<Transfers> transfersList = transactionRepository.getTransfers();

        assertEquals(1, transfersList.size());

        Transfers transfersListed = transfersList.get(0);

        assertEquals(transfersSaved.getId(), transfersListed.getId());
        assertEquals(transfersSaved.getPayablesId(), transfersListed.getPayablesId());
        assertEquals(transfersSaved.getReceivablesId(), transfersListed.getReceivablesId());
    }

    @Test
    public void testGetTransfersEmpty() {
        List<Transfers> transfers = transactionRepository.getTransfers();

        assertTrue(transfers.isEmpty());
    }

    @Test
    public void testGetExistingPayables() {
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Payables payables = new Payables();
        payables.setAccountId(accountId);
        payables.setAmount(amount);
        payables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Payables payablesSaved = transactionRepository.save(payables, transaction);
        transaction.commit();

        Optional<Payables> payablesOpt = transactionRepository.getPayables(payablesSaved.getId());

        assertTrue(payablesOpt.isPresent());

        Payables payablesListed = payablesOpt.get();

        assertEquals(payablesSaved.getId(), payablesListed.getId());
        assertEquals(payablesSaved.getAccountId(), payablesListed.getAccountId());
        assertEquals(payablesSaved.getAmount().doubleValue(), payablesListed.getAmount().doubleValue());
        assertEquals(payablesSaved.getCurrency(), payablesListed.getCurrency());
    }

    @Test
    public void testGetNonExistingPayables() {
        Optional<Payables> payablesOpt = transactionRepository.getPayables(1L);

        assertTrue(payablesOpt.isEmpty());
    }

    @Test
    public void testGetExistingReceivables() {
        Long accountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10.50);
        String currency = "EUR";

        Receivables receivables = new Receivables();
        receivables.setAccountId(accountId);
        receivables.setAmount(amount);
        receivables.setCurrency(currency);

        Transaction transaction = transactionRepository.getTransaction();
        Receivables receivablesSaved = transactionRepository.save(receivables, transaction);
        transaction.commit();

        Optional<Receivables> receivablesOpt = transactionRepository.getReceivables(receivablesSaved.getId());

        assertTrue(receivablesOpt.isPresent());

        Receivables receivablesListed = receivablesOpt.get();

        assertEquals(receivablesSaved.getId(), receivablesListed.getId());
        assertEquals(receivablesSaved.getAccountId(), receivablesListed.getAccountId());
        assertEquals(receivablesSaved.getAmount().doubleValue(), receivablesListed.getAmount().doubleValue(), 0.0);
        assertEquals(receivablesSaved.getCurrency(), receivablesListed.getCurrency());
    }

    @Test
    public void testGetNonExistingReceivables() {
        Optional<Receivables> receivablesOpt = transactionRepository.getReceivables(1L);

        assertTrue(receivablesOpt.isEmpty());
    }

}
