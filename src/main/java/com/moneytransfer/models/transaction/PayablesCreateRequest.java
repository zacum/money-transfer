package com.moneytransfer.models.transaction;

import com.google.gson.annotations.SerializedName;

public class PayablesCreateRequest extends TransactionCreateRequest {

    @SerializedName("account_id")
    private Long accountId = 0L;

    public PayablesCreateRequest(TransfersCreateRequest transfersCreateRequest) {
        accountId = transfersCreateRequest.getFromAccountId();
        amount = transfersCreateRequest.getAmount();
        currency = transfersCreateRequest.getCurrency();
    }

    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "PayablesCreateRequest{" +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
