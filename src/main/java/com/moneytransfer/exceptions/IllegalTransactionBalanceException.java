package com.moneytransfer.exceptions;

public class IllegalTransactionBalanceException extends IllegalTransactionException {

    public IllegalTransactionBalanceException(String message) {
        super(message);
    }

}
