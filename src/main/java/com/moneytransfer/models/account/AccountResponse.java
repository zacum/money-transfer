package com.moneytransfer.models.account;

import com.moneytransfer.entities.Account;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class AccountResponse {

    private Long id;

    private String name;

    private BigDecimal amount;

    private String currency;

    public AccountResponse() {
    }

    public AccountResponse(Account account) {
        Money money = Money.of(account.getAmount(), account.getCurrency());
        this.id = account.getId();
        this.name = account.getName();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

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

    public String getCurrency() {
        return currency;
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
