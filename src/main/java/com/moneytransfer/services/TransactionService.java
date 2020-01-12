package com.moneytransfer.services;

import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import com.moneytransfer.exceptions.IllegalTransactionAccountException;
import com.moneytransfer.exceptions.IllegalTransactionOperationTypeException;
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
            transactionRepository.save(getTransferTransaction(transactionCreateRequest));
        } else {
            throw new IllegalTransactionOperationTypeException("Operation Type " + operation + " is not supported");
        }
    }

    private Receivables getDepositTransaction(TransactionCreateRequest transactionCreateRequest) {
        if (transactionCreateRequest.getToAccountId() == null) {
            throw new IllegalTransactionAccountException("Receiving account id is required for DEPOSIT operation");
        }

        Receivables receivables = new Receivables();
        receivables.setAccountId(transactionCreateRequest.getToAccountId());
        receivables.setMoney(Money.of(transactionCreateRequest.getAmount(), transactionCreateRequest.getCurrency()));
        return receivables;
    }

    private Payables getWithdrawTransaction(TransactionCreateRequest transactionCreateRequest) {
        if (transactionCreateRequest.getFromAccountId() == null) {
            throw new IllegalTransactionAccountException("Paying account id is required for WITHDRAW operation");
        }

        Payables payables = new Payables();
        payables.setAccountId(transactionCreateRequest.getFromAccountId());
        payables.setMoney(Money.of(transactionCreateRequest.getAmount(), transactionCreateRequest.getCurrency()));
        return payables;
    }

    private Transfers getTransferTransaction(TransactionCreateRequest transactionCreateRequest) {
        if (transactionCreateRequest.getToAccountId() == null) {
            throw new IllegalTransactionAccountException("Receiving account id is required for TRANSFER operation");
        }
        if (transactionCreateRequest.getFromAccountId() == null) {
            throw new IllegalTransactionAccountException("Paying account id is required for TRANSFER operation");
        }

        Transfers transfers = new Transfers();
        transfers.setPayablesId(0L);
        transfers.setReceivablesId(0L);
        return transfers;
    }

}
