package com.moneytransfer.exceptions;

public class TransactionNegativeAmountException extends RuntimeException {

    public TransactionNegativeAmountException(String message) {
        super(message);
    }

}
