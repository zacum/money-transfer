package com.moneytransfer.models.account;

public class AccountCreateRequest {

    private String name = "";

    private String currency = "";

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "AccountCreateRequest{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }

}
