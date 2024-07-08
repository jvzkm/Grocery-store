package com.store.rest.controllers;

import com.store.model.security.Role;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.TYPE_HEADER;
import static com.store.util.constants.TestConstants.*;
import static com.store.util.constants.classes.BankTestConstants.NOT_FND;
import static com.store.util.constants.classes.ContractTestConstants.requestDto1;
import static com.store.util.constants.classes.ContractTestConstants.requestDto3;
import static com.store.util.constants.classes.ProviderTestConstants.CARD_PROVIDER_2;
import static com.store.util.constants.classes.ProviderTestConstants.CARD_PROVIDER_3;
import static com.store.util.constants.classes.ProviderTestConstants.cardProviderDto1;
import static com.store.util.constants.classes.SaleTestConstants.saleRequest2;
import static com.store.util.constants.classes.WorkerTestConstants.VALID_WORKER;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

class ExceptionAbstractControllerTest extends AbstractControllerTest {

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN" )
    void handleRequestHeaderException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(NOT_ACCEPTABLE);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void handleBankAccountNotFoundException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("/conf-service/bank/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "WORKER")
    void handleIllegalSaleException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(saleRequest2)
                .when()
                .post("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(BAD_REQUEST);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "PROVIDER")
    void handleIllegalTransactionException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(BAD_REQUEST);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "PROVIDER")
    void handleInsufficientFundsException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(requestDto3)
                .when()
                .post(format("/conf-service/provider/%s", CARD_PROVIDER_2))
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "USER")
    void handleHttpMediaTypeNotAcceptable(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header("Accept", TEXT_PLAIN)
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(NOT_ACCEPTABLE);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void handleNoHandlerFoundException(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("NOTHING_HERE")
                .then()
                .statusCode(NOT_FOUND);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "ADMIN")
    void handleHttpMessageNotReadable(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(MALFORMED)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(BAD_REQUEST);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class , names = "ADMIN")
    void handleGlobalException(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(EMPTY)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(ERROR);
    }
}