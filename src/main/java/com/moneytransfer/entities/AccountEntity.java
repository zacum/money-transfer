package com.moneytransfer.entities;

import com.google.common.base.Objects;
import com.moneytransfer.models.CurrencyType;

public class AccountEntity {

    private Long id;
    private String name;
    private CurrencyType currency;

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

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity)) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, currency);
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }

}
