package com.moneytransfer.entities;

import com.google.common.base.Objects;
import com.moneytransfer.models.transaction.PayablesCreateRequest;
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

    public Payables() {
    }

    public Payables(PayablesCreateRequest payablesCreateRequest) {
        Money money = Money.of(payablesCreateRequest.getAmount(), payablesCreateRequest.getCurrency());
        this.accountId = payablesCreateRequest.getAccountId();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

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
        if (!(o instanceof Payables)) return false;
        Payables payables = (Payables) o;
        return Objects.equal(id, payables.id) &&
                Objects.equal(accountId, payables.accountId) &&
                Objects.equal(amount, payables.amount) &&
                Objects.equal(currency, payables.currency);
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
