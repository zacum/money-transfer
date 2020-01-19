package com.moneytransfer.services;

import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
import com.moneytransfer.models.transaction.ReceivablesCreateRequest;
import com.moneytransfer.models.transaction.TransactionResponse;
import com.moneytransfer.models.transaction.TransfersCreateRequest;
import com.moneytransfer.repositories.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {

    @Inject
    private AccountService accountService;

    @Inject
    private TransactionRepository transactionRepository;

    public TransactionResponse createPayables(PayablesCreateRequest payablesCreateRequest) {
        Payables payables = new Payables(payablesCreateRequest);
        Transaction transaction = transactionRepository.getTransaction();
        try {
            accountService.withdraw(payables, transaction);
            payables = transactionRepository.save(payables, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(payables);
    }

    public TransactionResponse createReceivables(ReceivablesCreateRequest receivablesCreateRequest) {
        Receivables receivables = new Receivables(receivablesCreateRequest);
        Transaction transaction = transactionRepository.getTransaction();
        try {
            accountService.deposit(receivables, transaction);
            receivables = transactionRepository.save(receivables, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(receivables);
    }

    public TransactionResponse createTransfers(TransfersCreateRequest transfersCreateRequest) {
        Payables payables = new Payables(new PayablesCreateRequest(transfersCreateRequest));
        Receivables receivables = new Receivables(new ReceivablesCreateRequest(transfersCreateRequest));
        Transfers transfers = new Transfers();
        Transaction transaction = transactionRepository.getTransaction();
        try {
            accountService.withdraw(payables, transaction);
            accountService.deposit(receivables, transaction);
            payables = transactionRepository.save(payables, transaction);
            receivables = transactionRepository.save(receivables, transaction);
            transfers.setPayablesId(payables.getId());
            transfers.setReceivablesId(receivables.getId());
            transfers = transactionRepository.save(transfers, transaction);
            transaction.commit();
        } catch (Throwable t) {
            transaction.rollback();
            throw t;
        }
        return new TransactionResponse(payables, receivables, transfers);
    }

    public List<TransactionResponse> getPayables() {
        return transactionRepository.getPayables().stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getReceivables() {
        return transactionRepository.getReceivables().stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransfers() {
        return transactionRepository.getTransfers().stream()
                .map(transfers -> {
                    Optional<Payables> payables = transactionRepository.getPayables(transfers.getPayablesId());
                    Optional<Receivables> receivables = transactionRepository.getReceivables(transfers.getReceivablesId());
                    return new TransactionResponse(payables.get(), receivables.get(), transfers);
                })
                .collect(Collectors.toList());
    }

}
