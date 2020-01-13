package com.moneytransfer.models.transaction;

import java.math.BigDecimal;

public abstract class TransactionCreateRequest {

    protected BigDecimal amount = BigDecimal.ZERO;

    protected String currency = "";

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

}
