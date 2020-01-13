package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransactionResponse;
import com.moneytransfer.models.transaction.TransfersCreateRequest;
import com.moneytransfer.repositories.TransactionRepository;
import org.javamoney.moneta.Money;

public class TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    public TransactionResponse createPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = getPayables(payablesCreateRequest);
        return new TransactionResponse(transactionRepository.save(payables));
    }

    public TransactionResponse createReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = getReceivables(receivablesCreateRequest);
        return new TransactionResponse(transactionRepository.save(receivables));
    }

    public TransactionResponse createTransfers(TransfersCreateRequest transfersCreateRequest) {
        Payables payables = getPayables(new PayablesCreateRequest(transfersCreateRequest));
        Receivables receivables = getReceivables(new ReceivablesCreateRequest(transfersCreateRequest));
        return new TransactionResponse(transactionRepository.save(payables, receivables, new Transfers()));
    }

    private Payables getPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = new Payables();
        payables.setAccountId(payablesCreateRequest.getAccountId());
        payables.setMoney(Money.of(payablesCreateRequest.getAmount(), payablesCreateRequest.getCurrency()));
        return payables;
    }

    private Receivables getReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = new Receivables();
        receivables.setAccountId(receivablesCreateRequest.getAccountId());
        receivables.setMoney(Money.of(receivablesCreateRequest.getAmount(), receivablesCreateRequest.getCurrency()));
        return receivables;
    }

}
