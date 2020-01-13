package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransfersCreateRequest;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.repositories.TransactionRepository;
import org.javamoney.moneta.Money;

public class TransactionService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private TransactionRepository transactionRepository;

    public void savePayables(PayablesCreateRequest payablesCreateRequest) {
        transactionRepository.save(getPayables(payablesCreateRequest));
    }

    private Payables getPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = new Payables();
        payables.setAccountId(payablesCreateRequest.getAccountId());
        payables.setMoney(Money.of(payablesCreateRequest.getAmount(), payablesCreateRequest.getCurrency()));
        return payables;
    }

    public void saveReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        transactionRepository.save(getReceivables(receivablesCreateRequest));
    }

    private Receivables getReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = new Receivables();
        receivables.setAccountId(receivablesCreateRequest.getAccountId());
        receivables.setMoney(Money.of(receivablesCreateRequest.getAmount(), receivablesCreateRequest.getCurrency()));
        return receivables;
    }

    public void saveTransfers(TransfersCreateRequest transfersCreateRequest) {
        Payables payables = getPayables(new PayablesCreateRequest(transfersCreateRequest));
        Receivables receivables = getReceivables(new ReceivablesCreateRequest(transfersCreateRequest));
        transactionRepository.save(payables, receivables);
    }

}
