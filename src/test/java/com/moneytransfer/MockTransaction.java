package com.moneytransfer;

import com.dieselpoint.norm.Transaction;

import java.io.IOException;
import java.sql.Connection;

public class MockTransaction extends Transaction {

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void close() throws IOException {
    }

}
