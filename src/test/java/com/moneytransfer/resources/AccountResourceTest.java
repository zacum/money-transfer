package com.moneytransfer.resources;

import com.moneytransfer.Main;
import com.moneytransfer.ResourcesUtils;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class AccountResourceTest {

    @BeforeClass
    public static void setUp() {
        Main.main(new String[] {});
    }

    @Test
    public void testAccountCreateEndpoint() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/account_create_request.json");
        String responseBody = ResourcesUtils.getString("./src/test/resources/account_create_response.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/account")
                .body(requestBody)
                .asString();

        assertEquals(201, response.getStatus());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    public void testAccountCreateEndpointMalformedJson() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/account_create_request_malformed.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/account")
                .body(requestBody)
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Invalid JSON", response.getBody());
    }

    @Test
    public void testAccountCreateEndpointIncorrectCurrency() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/account_create_request_incorrect_currency.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/account")
                .body(requestBody)
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Invalid currency code", response.getBody());
    }

    @Test
    public void testAccountListEmptyEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/account")
                .asString();

        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getBody());
    }

    @Test
    public void testAccountGetMalformedEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/account/a")
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Account id is not a number", response.getBody());
    }

    @Test
    public void testAccountGetExistingEndpoint() throws IOException {
        String responseBody = ResourcesUtils.getString("./src/test/resources/account_create_response.json");

        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/account/1")
                .asString();

        assertEquals(200, response.getStatus());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    public void testAccountGetNonExistingEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/account/5")
                .asString();

        assertEquals(404, response.getStatus());
        assertEquals("\"\"", response.getBody());
    }

}
