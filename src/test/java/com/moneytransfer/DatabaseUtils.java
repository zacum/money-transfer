package com.moneytransfer;

import com.dieselpoint.norm.Database;

public class DatabaseUtils {

    private static Database database;

    static {
        System.setProperty("norm.jdbcUrl", "jdbc:h2:mem:moneytransfer;database_to_upper=false");
        System.setProperty("norm.user", "");
        System.setProperty("norm.password", "");

        database = new Database();
    }

    public static Database getDatabase() {
        return database;
    }

}
