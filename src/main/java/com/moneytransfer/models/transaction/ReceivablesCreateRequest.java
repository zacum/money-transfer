package com.moneytransfer.models.transaction;

import com.google.gson.annotations.SerializedName;

public class ReceivablesCreateRequest extends TransactionCreateRequest {

    @SerializedName("account_id")
    private Long accountId = 0L;

    public ReceivablesCreateRequest() {
    }

    public ReceivablesCreateRequest(TransfersCreateRequest transfersCreateRequest) {
        accountId = transfersCreateRequest.getToAccountId();
        amount = transfersCreateRequest.getAmount();
        currency = transfersCreateRequest.getCurrency();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "ReceivablesCreateRequest{" +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
