package com.moneytransfer.entities;

import com.google.common.base.Objects;
import com.moneytransfer.models.account.AccountCreateRequest;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount;

    private String currency;

    public Account() {
    }

    public Account(AccountCreateRequest accountCreateRequest) {
        this.name = accountCreateRequest.getName();
        this.setMoney(Money.of(0, accountCreateRequest.getCurrency()));
    }

    @Id
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

    public void setMoney(Money money) {
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equal(id, account.id) &&
                Objects.equal(name, account.name) &&
                Objects.equal(amount, account.amount) &&
                Objects.equal(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, amount, currency);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
