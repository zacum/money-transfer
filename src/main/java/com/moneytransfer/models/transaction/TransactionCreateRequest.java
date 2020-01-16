package com.moneytransfer.models.transaction;

import java.math.BigDecimal;

public abstract class TransactionCreateRequest {

    protected BigDecimal amount = BigDecimal.valueOf(0.0);

    protected String currency = "";

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

}
