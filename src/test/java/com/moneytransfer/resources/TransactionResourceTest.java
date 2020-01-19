package com.moneytransfer.resources;

import com.moneytransfer.Main;
import com.moneytransfer.ResourcesUtils;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TransactionResourceTest {

    @BeforeClass
    public static void setUp() {
        Main.main(new String[] {});
    }

    @Test
    public void testPayablesCreateEndpointNonExistingAccount() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/payables_create_request.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/payables")
                .body(requestBody)
                .asString();

        assertEquals(404, response.getStatus());
        assertEquals("Account is not found", response.getBody());
    }

    @Test
    public void testPayablesCreateEndpointLowBalanceAccount() throws IOException {
        String requestAccountBody = ResourcesUtils.getString("./src/test/resources/account_create_request.json");

        Unirest
                .post("http://localhost:4567/account")
                .body(requestAccountBody)
                .asString();

        String requestBody = ResourcesUtils.getString("./src/test/resources/payables_create_request_high_amount.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/payables")
                .body(requestBody)
                .asString();

        assertEquals(409, response.getStatus());
        assertEquals("Paying account does not have sufficient funds", response.getBody());
    }

    @Test
    public void testPayablesCreateEndpointExistingAccount() throws IOException {
        String requestAccountBody = ResourcesUtils.getString("./src/test/resources/account_create_request.json");

        Unirest
                .post("http://localhost:4567/account")
                .body(requestAccountBody)
                .asString();

        String requestReceivablesBody = ResourcesUtils.getString("./src/test/resources/receivables_create_request.json");

        Unirest
                .post("http://localhost:4567/transaction/receivables")
                .body(requestReceivablesBody)
                .asString();

        String requestBody = ResourcesUtils.getString("./src/test/resources/payables_create_request.json");
        String responseBody = ResourcesUtils.getString("./src/test/resources/payables_create_response.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/payables")
                .body(requestBody)
                .asString();

        assertEquals(201, response.getStatus());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    public void testPayablesCreateEndpointNegativeAmount() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/payables_create_request_negative_amount.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/payables")
                .body(requestBody)
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Amount needs to be positive", response.getBody());
    }

    @Test
    public void testReceivablesCreateEndpointExistingAccount() throws IOException {
        String requestAccountBody = ResourcesUtils.getString("./src/test/resources/account_create_request.json");

        Unirest
                .post("http://localhost:4567/account")
                .body(requestAccountBody)
                .asString();

        String requestReceivablesBody = ResourcesUtils.getString("./src/test/resources/receivables_create_request.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/receivables")
                .body(requestReceivablesBody)
                .asString();

        String responseBody = ResourcesUtils.getString("./src/test/resources/receivables_create_response.json");

        assertEquals(201, response.getStatus());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    public void testReceivablesCreateEndpointNegativeAmount() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/receivables_create_request_negative_amount.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/receivables")
                .body(requestBody)
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Amount needs to be positive", response.getBody());
    }

    @Test
    public void testTransfersCreateEndpointExistingAccount() throws IOException {
        String requestAccountBody = ResourcesUtils.getString("./src/test/resources/account_create_request.json");

        Unirest
                .post("http://localhost:4567/account")
                .body(requestAccountBody)
                .asString();

        String requestTransfersBody = ResourcesUtils.getString("./src/test/resources/transfers_create_request.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/transfers")
                .body(requestTransfersBody)
                .asString();

        String responseBody = ResourcesUtils.getString("./src/test/resources/transfers_create_response.json");

        assertEquals(201, response.getStatus());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    public void testTransfersCreateEndpointNegativeAmount() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/transfers_create_request_negative_amount.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/transfers")
                .body(requestBody)
                .asString();

        assertEquals(400, response.getStatus());
        assertEquals("Amount needs to be positive", response.getBody());
    }

    @Test
    public void testTransfersCreateEndpointSameAccount() throws IOException {
        String requestBody = ResourcesUtils.getString("./src/test/resources/transfers_create_request_same_account.json");

        HttpResponse<String> response = Unirest
                .post("http://localhost:4567/transaction/transfers")
                .body(requestBody)
                .asString();

        assertEquals(409, response.getStatus());
        assertEquals("Cannot transfer money to and from the same account", response.getBody());
    }

    @Test
    public void testPayablesListNonEmptyEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/transaction/payables")
                .asString();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testReceivablesListNonEmptyEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/transaction/receivables")
                .asString();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testTransfersListNonEmptyEndpoint() {
        HttpResponse<String> response = Unirest
                .get("http://localhost:4567/transaction/transfers")
                .asString();

        assertEquals(200, response.getStatus());
    }

}
