package com.moneytransfer.models.transaction;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class TransactionCreateRequest {

    private OperationType operation;

    @SerializedName("from_account_id")
    private Long fromAccountId;

    @SerializedName("to_account_id")
    private Long toAccountId;

    private BigDecimal amount;

    private String currency;

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
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
        return "TransactionCreateRequest{" +
                "operation=" + operation +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
