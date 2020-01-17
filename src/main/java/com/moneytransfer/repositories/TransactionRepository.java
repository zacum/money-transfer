package com.moneytransfer.repositories;

import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Transaction;
import com.google.inject.Inject;
import com.moneytransfer.entities.Payables;
import com.moneytransfer.entities.Receivables;
import com.moneytransfer.entities.Transfers;

import java.util.List;
import java.util.Optional;

public class TransactionRepository {

    @Inject
    private Database database;

    public Transaction getTransaction() {
        return database.startTransaction();
    }

    public void close() {
        database.close();
    }

    public Payables save(Payables payables, Transaction transaction) {
        database
                .transaction(transaction)
                .table("payables")
                .generatedKeyReceiver(payables, "id")
                .insert(payables);
        return payables;
    }

    public Receivables save(Receivables receivables, Transaction transaction) {
        database
                .transaction(transaction)
                .table("receivables")
                .generatedKeyReceiver(receivables, "id")
                .insert(receivables);
        return receivables;
    }

    public Transfers save(Transfers transfers, Transaction transaction) {
        database
                .transaction(transaction)
                .table("transfers")
                .generatedKeyReceiver(transfers, "id")
                .insert(transfers);
        return transfers;
    }

    public List<Payables> getPayables() {
        return database
                .table("payables")
                .orderBy("id")
                .results(Payables.class);
    }

    public List<Receivables> getReceivables() {
        return database
                .table("receivables")
                .orderBy("id")
                .results(Receivables.class);
    }

    public List<Transfers> getTransfers() {
        return database
                .table("transfers")
                .orderBy("id")
                .results(Transfers.class);
    }

    public Optional<Payables> getPayables(Long payablesId) {
        return database
                .table("payables")
                .where("id=?", payablesId)
                .results(Payables.class)
                .stream()
                .findFirst();
    }

    public Optional<Receivables> getReceivables(Long receivablesId) {
        return database
                .table("receivables")
                .where("id=?", receivablesId)
                .results(Receivables.class)
                .stream()
                .findFirst();
    }

}
