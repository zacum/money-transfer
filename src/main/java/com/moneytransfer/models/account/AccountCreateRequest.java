package com.moneytransfer.models.account;

import com.moneytransfer.models.CurrencyType;

public class AccountCreateRequest {

    private String name;
    private CurrencyType currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
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
