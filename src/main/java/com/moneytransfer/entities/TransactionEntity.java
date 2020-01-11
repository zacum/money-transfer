package com.moneytransfer.entities;

import com.google.common.base.Objects;
import org.javamoney.moneta.Money;

public class TransactionEntity {

    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private Money money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity)) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(fromAccountId, that.fromAccountId) &&
                Objects.equal(toAccountId, that.toAccountId) &&
                Objects.equal(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, fromAccountId, toAccountId, money);
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "id=" + id +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", money=" + money +
                '}';
    }

}
