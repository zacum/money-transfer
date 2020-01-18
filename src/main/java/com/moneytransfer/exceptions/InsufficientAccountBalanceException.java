package com.moneytransfer.exceptions;

public class InsufficientAccountBalanceException extends RuntimeException {

    public InsufficientAccountBalanceException(String message) {
        super(message);
    }

}
