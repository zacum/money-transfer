package com.moneytransfer.models.account;

public class AccountCreateRequest {

    private String name;
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "AccountCreateRequest{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }

}
