package com.moneytransfer.exceptions;

public class NegativeAmountTransactionException extends RuntimeException {

    public NegativeAmountTransactionException(String message) {
        super(message);
    }

}
