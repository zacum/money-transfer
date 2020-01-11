package com.moneytransfer.models.account;

import java.math.BigDecimal;

public class AccountResponse {

    private Long id;
    private String name;
    private BigDecimal amount;
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "AccountCreateResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

}
