package com.moneytransfer.exceptions;

public class AccountBalanceException extends RuntimeException {

    public AccountBalanceException(String message) {
        super(message);
    }

}
