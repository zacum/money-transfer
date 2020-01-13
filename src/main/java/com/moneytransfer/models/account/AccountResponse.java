package com.moneytransfer.models.account;

import com.moneytransfer.entities.Account;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class AccountResponse {

    private Long id;

    private String name;

    private BigDecimal amount;

    private String currency;

    public AccountResponse(Account account) {
        Money money = Money.of(account.getAmount(), account.getCurrency());
        this.id = account.getId();
        this.name = account.getName();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
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
