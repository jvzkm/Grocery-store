package com.store.rest.controllers;

import org.junit.jupiter.api.Test;

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.TYPE;
import static com.store.util.constants.TestConstants.*;
import static com.store.util.constants.classes.BankTestConstants.NOT_FND;
import static com.store.util.constants.classes.ContractTestConstants.requestDto1;
import static com.store.util.constants.classes.ContractTestConstants.requestDto3;
import static com.store.util.constants.classes.ProviderTestConstants.CARD_PROVIDER_2;
import static com.store.util.constants.classes.ProviderTestConstants.CARD_PROVIDER_3;
import static com.store.util.constants.classes.ProviderTestConstants.CASH_PROVIDER_1;
import static com.store.util.constants.classes.ProviderTestConstants.cardProviderDto1;
import static com.store.util.constants.classes.SaleTestConstants.saleRequest2;
import static com.store.util.constants.classes.WorkerTestConstants.VALID_WORKER;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

class ExceptionControllerTest extends ControllerTest {

    @Test
    void handleRequestHeaderException() {
        given()
                .header(TYPE, RANDOM)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(NOT_ACCEPTABLE);
    }

    @Test
    void handleBankAccountNotFoundException() {
        given()
                .when()
                .get(format("/conf-service/bank/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);
    }

    @Test
    void handleIllegalSaleException() {
        given()
                .contentType(APP_JSON)
                .body(saleRequest2)
                .when()
                .post("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(BAD_REQUEST);
    }

    @Test
    void handleIllegalTransactionException() {
        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(BAD_REQUEST);
    }

    @Test
    void handleInsufficientFundsException() {
        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(requestDto3)
                .when()
                .post(format("/conf-providerService/provider/%s", CARD_PROVIDER_2))
                .then()
                .statusCode(FORBIDDEN);

    }

    @Test
    void handleHttpMediaTypeNotAcceptable() {
        given()
                .header("Accept", TEXT_PLAIN)
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(NOT_ACCEPTABLE);
    }

    @Test
    void handleNoHandlerFoundException() {
        given()
                .when()
                .get("NOTHING_HERE")
                .then()
                .statusCode(NOT_FOUND);
    }

    @Test
    void handleHttpMessageNotReadable() {
        given()
                .contentType(APP_JSON)
                .body(MALFORMED)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(BAD_REQUEST);
    }

    @Test
    void handleGlobalException() {
        given()
                .contentType(APP_JSON)
                .body(EMPTY)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(ERROR);
    }
}