package com.moneytransfer.models.transaction;

import com.google.gson.annotations.SerializedName;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class TransactionResponse {

    private Long id;

    @SerializedName("from_account_id")
    private Long fromAccountId;

    @SerializedName("to_account_id")
    private Long toAccountId;

    @SerializedName("payables_id")
    private Long payablesId;

    @SerializedName("receivables_id")
    private Long receivablesId;

    private BigDecimal amount;

    private String currency;

    public TransactionResponse(Payables payables) {
        Money money = Money.of(payables.getAmount(), payables.getCurrency());
        this.id = payables.getId();
        this.fromAccountId = payables.getAccountId();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

    public TransactionResponse(Receivables receivables) {
        Money money = Money.of(receivables.getAmount(), receivables.getCurrency());
        this.id = receivables.getId();
        this.toAccountId = receivables.getAccountId();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

    public TransactionResponse(Payables payables, Receivables receivables, Transfers transfers) {
        Money money = Money.of(payables.getAmount(), payables.getCurrency());
        this.id = transfers.getId();
        this.fromAccountId = payables.getAccountId();
        this.toAccountId = receivables.getAccountId();
        this.payablesId = transfers.getPayablesId();
        this.receivablesId = transfers.getReceivablesId();
        this.amount = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        this.currency = money.getCurrency().getCurrencyCode();
    }

    @Override
    public String toString() {
        return "TransactionResonse{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

}
