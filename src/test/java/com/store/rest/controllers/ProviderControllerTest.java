package com.store.rest.controllers;

import com.store.exceptions.IllegalTransactionException;
import com.store.exceptions.RequestHeaderException;
import com.store.model.entity.CardProvider;
import com.store.model.security.Role;
import com.store.service.ProviderService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.ProviderConstants.CARD;
import static com.store.util.ProviderConstants.CASH;
import static com.store.util.ProviderConstants.TYPE_HEADER;
import static com.store.util.constants.TestConstants.*;
import static com.store.util.constants.classes.ContractTestConstants.requestDto1;
import static com.store.util.constants.classes.ProviderTestConstants.*;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderControllerTest extends AbstractControllerTest {

    @Autowired
    private ProviderService providerService;

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void getAllProvidersNoHeader(Role role) {
        setup(role);
        var expected = providerService.getAllProviders();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllProvidersNoHeaderButNoAuthorization(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    void getAllProvidersNoHeaderButNoJwtToken(Role role) {
        given()
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void getAllCashProviders(Role role) {
        setup(role);
        var expected = providerService.getCashProviders();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllCashProvidersButNoAuthorization(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    void getAllCASHProvidersButNoJwtToken(Role role) {
        given()
                .header(TYPE_HEADER, CASH)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void getAllCardProviders(Role role) {
        setup(role);
        var expected = providerService.getCardProviders();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllCardProvidersButNoAuthorization(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    void getAllCARDProvidersButNoJwtToken(Role role) {
        given()
                .header(TYPE_HEADER, CARD)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void getProvidersWithInvalidHeader(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.getProvidersByType(RANDOM)
        );

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void getProvidersWithInvalidHeaderButNoAuthorization(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .when()
                .get("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);
    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void shouldGetCardProvider(Role role) {
        setup(role);
        var expected = cardProvider3;

        providerService.getProvider(CARD_PROVIDER_3, CardProvider.class);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .when()
                .get(format("/conf-service/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void shouldGetCardProviderButNoAthorization(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .when()
                .get(format("/conf-service/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void shouldGetCashProvider(Role role) {
        setup(role);
        var expected = cashProvider1;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .when()
                .get(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void shouldGetCashProviderButNoAuthorization(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .when()
                .get(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"})
    void getProviderWithInvalidHeader(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .when()
                .get(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.getProviderByType(CASH_PROVIDER_1, RANDOM)
        );
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "PROVIDER"}, mode = EnumSource.Mode.EXCLUDE)
    void getProviderWithInvalidHeaderButNoAuthority(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .when()
                .get(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(FORBIDDEN);
    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void addCashProvider(Role role) {
        setup(role);
        var expected = cashProviderDto1;

        providerService.saveProviderByType(cardProviderDto1, CASH);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .contentType(APP_JSON)
                .body(cashProviderDto1)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(OK)
                .and()
                .body("name", equalTo(expected.getName()));


    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void addCashProviderButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .contentType(APP_JSON)
                .body(cashProviderDto1)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);

    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void addCardProvider(Role role) {
        setup(role);
        var expected = cardProviderDto1;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(OK)
                .body("name", equalTo(expected.getName()));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void addCardProviderButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(cardProviderDto1)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void addCardProviderButSharedBankAccount(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(cardProviderDto2)
                .when()
                .post("/conf-service/provider")
                .then()
                .statusCode(CONFLICT);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void addProviderWithInvalidHeader(Role role) {
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

        assertThrows(RequestHeaderException.class,
                () -> providerService.saveProviderByType(cardProviderDto1, RANDOM)
        );



    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER")
    void createCashContract(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER", mode = EnumSource.Mode.EXCLUDE)
    void createCashContractButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CASH)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CASH_PROVIDER_1))
                .then()
                .statusCode(FORBIDDEN);
    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER")
    void createCardContract(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CARD_PROVIDER_2))
                .then()
                .statusCode(OK)
                .body(emptyString());

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER", mode = EnumSource.Mode.EXCLUDE)
    void createCardContractButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, CARD)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CARD_PROVIDER_2))
                .then()
                .statusCode(FORBIDDEN);

    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER")
    void createCardContractButBankAccountNotActive(Role role) {
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

        assertThrows(IllegalTransactionException.class,
                () -> providerService.createContractByType(CARD_PROVIDER_3, CARD, requestDto1)
        );

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "PROVIDER")
    void createContractButInvalidHeader(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .header(TYPE_HEADER, RANDOM)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post(format("/conf-service/provider/%s", CARD_PROVIDER_3))
                .then()
                .statusCode(NOT_ACCEPTABLE);

        assertThrows(RequestHeaderException.class,
                () -> providerService.createContractByType(CARD_PROVIDER_3, RANDOM, requestDto1)
        );
    }


}