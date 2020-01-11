package com.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static spark.Spark.after;

public class Main {

    public static void main(String[] args) throws SQLException {
        after(((request, response) -> response.type("application/json")));

        Injector injector = Guice.createInjector(new GuiceConfiguration());
        injector.getInstance(RestAPI.class).run();

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:moneytransfer;INIT=runscript from './src/main/resources/schema.sql'", "", "");
    }

}
