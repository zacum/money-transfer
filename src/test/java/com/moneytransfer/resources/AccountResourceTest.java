package com.moneytransfer.resources;

import com.moneytransfer.Main;
import org.junit.Test;
import spark.Spark;

import java.net.http.HttpResponse;

public class AccountResourceTest {

    @Test
    public void testAccountDefaultConstructor() {
        String[] args = new String[] {};
        Main.main(args);

        Spark.awaitInitialization();

        HttpResponse<String> response = Unirest.post("http://localhost:4567/account")
                .header("Content-Type", "application/json")
                .body("{\n\t\"name\": \"Victor Account 1\",\n\t\"currency\": \"EUR\"\n}\n")
                .asString();

    }

}
