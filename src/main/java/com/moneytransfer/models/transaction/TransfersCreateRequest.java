package com.moneytransfer.models.transaction;

import com.google.gson.annotations.SerializedName;

public class TransfersCreateRequest extends TransactionCreateRequest {

    @SerializedName("from_account_id")
    private Long fromAccountId = 0L;

    @SerializedName("to_account_id")
    private Long toAccountId = 0L;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    @Override
    public String toString() {
        return "TransfersCreateRequest{" +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
