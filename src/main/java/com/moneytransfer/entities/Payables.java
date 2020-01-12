package com.moneytransfer.entities;

import com.google.common.base.Objects;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "payables")
public class Payables {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    private BigDecimal amount;

    private String currency;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "account_id")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setMoney(Money money) {
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payables)) return false;
        Payables that = (Payables) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(accountId, that.accountId) &&
                Objects.equal(amount, that.amount) &&
                Objects.equal(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, accountId, amount, currency);
    }

    @Override
    public String toString() {
        return "Payables{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
