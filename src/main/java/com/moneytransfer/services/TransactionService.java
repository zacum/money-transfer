package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.exceptions.IllegalTransactionOperationType;
import com.moneytransfer.models.transaction.OperationType;
import com.moneytransfer.models.transaction.TransactionCreateRequest;
import com.moneytransfer.repositories.AccountRepository;
import com.moneytransfer.repositories.TransactionRepository;
import org.javamoney.moneta.Money;

public class TransactionService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private TransactionRepository transactionRepository;

    public void transferMoney(TransactionCreateRequest transactionCreateRequest) {
        OperationType operation = transactionCreateRequest.getOperation();
        if (operation == OperationType.DEPOSIT) {
            transactionRepository.save(getDepositTransaction(transactionCreateRequest));
        } else if (operation == OperationType.WITHDRAW) {
            transactionRepository.save(getWithdrawTransaction(transactionCreateRequest));
        } else if (operation == OperationType.TRANSFER) {
//            transactionRepository.save(transactionCreateRequest);
        }
        throw new IllegalTransactionOperationType("Operation Type " + operation + " is not supported");
    }

    private Receivables getDepositTransaction(TransactionCreateRequest transactionCreateRequest) {
        Receivables receivables = new Receivables();
        receivables.setAccountId(transactionCreateRequest.getToAccountId());
        receivables.setMoney(Money.of(transactionCreateRequest.getAmount(), transactionCreateRequest.getCurrency()));
        return receivables;
    }

    private Payables getWithdrawTransaction(TransactionCreateRequest transactionCreateRequest) {
        Payables payables = new Payables();
        payables.setAccountId(transactionCreateRequest.getFromAccountId());
        payables.setMoney(Money.of(transactionCreateRequest.getAmount(), transactionCreateRequest.getCurrency()));
        return payables;
    }

}
