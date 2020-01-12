package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;

public class TransactionRepository {

    @Inject
    private Database database;

    public void save(Receivables receivables) {
        database.table("receivables").insert(receivables);
    }

    public void save(Payables payables) {
        database.table("payables").insert(payables);
    }

    public void save(Transfers transfers) {
        database.table("transfers").insert(transfers);
    }

}
