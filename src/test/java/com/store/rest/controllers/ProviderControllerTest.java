package com.store.rest.controllers;

import com.store.exceptions.IllegalTransactionException;
import com.store.exceptions.RequestHeaderException;
import com.store.model.entity.CardProvider;
import com.store.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.CASH;
import static com.store.util.ProviderConstants.TYPE;
import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.BAD_REQUEST;
import static com.store.util.constants.TestConstants.ERROR;
import static com.store.util.constants.TestConstants.NOT_ACCEPTABLE;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.TestConstants.RANDOM;
import static com.store.util.constants.classes.ContractTestConstants.requestDto1;
import static com.store.util.constants.classes.ContractTestConstants.requestDto2;
import static com.store.util.constants.classes.ProviderTestConstants.*;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderControllerTest extends ControllerTest {

    @Autowired
    private ProviderService providerService;

    @Test
    void getAllProvidersNoHeader() {
        var expected = providerService.getAllProviders();

        given()
                .when()
                .get("/conf-providerService/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void getAllCashProviders() {
        var expected = providerService.getCashProviders();

        given()
                .header(TYPE, CASH)
                .when()
                .get("/conf-providerService/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void getAllCardProviders() {
        var expected = providerService.getCardProviders();

        given()
                .header(TYPE, CARD)
                .when()
                .get("/conf-providerService/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void getProvidersWithInvalidHeader() {

        given()
                .header(TYPE, RANDOM)
                .when()
                .get("/conf-providerService/provider")
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.getProvidersByType(RANDOM)
        );

    }


    @Test
    void shouldGetCardProvider() {
        var expected = cardProvider3;

        providerService.getProvider(CARD_PROVIDER_3, CardProvider.class);

        given()
                .header(TYPE, CARD)
                .when()
                .get(format("/conf-providerService/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void shouldGetCashProvider() {
        var expected = cashProvider1;

        given()
                .header(TYPE, CASH)
                .when()
                .get(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void getProviderWithInvalidHeader() {
        given()
                .header(TYPE, RANDOM)
                .when()
                .get(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.getProviderByType(CASH_PROVIDER_1, RANDOM)
        );
    }


    @Test
    void addCashProvider() {

        var expected = cashProviderDto1;

        providerService.saveProviderByType(cardProviderDto1, CASH);

        given()
                .header(TYPE, CASH)
                .contentType(APP_JSON)
                .body(cashProviderDto1)
                .when()
                .post("/conf-providerService/provider")
                .then()
                .statusCode(OK)
                .and()
                .body("name", equalTo(expected.getName()));


    }

    @Test
    void addCardProvider() {
        var expected = cardProviderDto1;

        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post("/conf-providerService/provider")
                .then()
                .statusCode(OK)
                .body("name", equalTo(expected.getName()));
    }

    @Test
    void addCardProviderButSharedBankAccount() {
        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(cardProviderDto2)
                .when()
                .post("/conf-providerService/provider")
                .then()
                .statusCode(ERROR);
    }

    @Test
    void addProviderWithInvalidHeader() {
        given()
                .header(TYPE, RANDOM)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(BAD_REQUEST);

        assertThrows(RequestHeaderException.class,
                () -> providerService.saveProviderByType(cardProviderDto1, RANDOM)
        );
    }

    @Test
    void createCashContract() {
        given()
                .header(TYPE, CASH)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

//    @Test
//    void createCashContractButBankAccountNotActive() {
//        given()
//                .header(TYPE, CASH)
//                .contentType(APP_JSON)
//                .body(requestDto2)
//                .when()
//                .post(format("/conf-providerService/provider/%s", CASH_PROVIDER_1))
//                .then()
//                .statusCode(BAD_REQUEST);
//
//        assertThrows(IllegalTransactionException.class,
//                () -> providerService.createContractByType(CASH_PROVIDER_1, CASH, requestDto2)
//        );
//
//    }


    @Test
    void createCardContract() {
        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CARD_PROVIDER_2))
                .then()
                .statusCode(OK)
                .body(emptyString());

    }

    @Test
    void createCardContractButBankAccountNotActive() {
        given()
                .header(TYPE, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(BAD_REQUEST);

        assertThrows(IllegalTransactionException.class,
                () -> providerService.createContractByType(CARD_PROVIDER_3, CARD, requestDto1)
        );

    }

    @Test
    void createContractButInvalidHeader() {
        given()
                .header(TYPE, RANDOM)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-providerService/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.createContractByType(CARD_PROVIDER_3, RANDOM, requestDto1)
        );
    }


}