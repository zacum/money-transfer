package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;

public class TransactionRepository {

    @Inject
    private Database database;

    public void save(Receivables receivables) {
        database.table("receivables").insert(receivables);
    }

    public void save(Payables payables) {
        database.table("payables").insert(payables);
    }

    public void transfer(Payables payables) {
        database.table("payables").insert(payables);
    }

}
