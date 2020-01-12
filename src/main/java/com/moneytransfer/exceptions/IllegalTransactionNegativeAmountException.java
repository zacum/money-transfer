package com.moneytransfer.exceptions;

public class IllegalTransactionNegativeAmountException extends IllegalTransactionException {

    public IllegalTransactionNegativeAmountException(String message) {
        super(message);
    }

}
