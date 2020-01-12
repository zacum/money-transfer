package com.moneytransfer.exceptions;

public class IllegalTransactionOperationType extends RuntimeException {

    public IllegalTransactionOperationType(String message) {
        super(message);
    }

}
