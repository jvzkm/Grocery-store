package com.store.rest.controllers;

import com.store.exceptions.BankAccountNotFound;
import com.store.model.security.Role;
import com.store.service.impl.BankServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.AUTHORIZATION;
import static com.store.util.constants.TestConstants.BEARER;
import static com.store.util.constants.TestConstants.FORBIDDEN;
import static com.store.util.constants.TestConstants.NOT_FOUND;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.BankTestConstants.ACCOUNT_ONE;
import static com.store.util.constants.classes.BankTestConstants.NOT_FND;
import static com.store.util.constants.classes.BankTestConstants.account1;
import static com.store.util.constants.classes.BankTestConstants.account2;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountControllerTest extends AbstractControllerTest {

    @Autowired
    private BankServiceImpl service;

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void shouldGetAllBankAccounts(Role role) {
        setup(role);
        var expected = service.getBankAllAccounts();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .get("/conf-service/bank")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void shouldGetAllBankAccountsButNoJwtToken() {
        var expected = service.getBankAllAccounts();

        given()
                .get("/conf-service/bank")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void shouldGetValidBankAccount(Role role) {
        setup(role);
        var expected = account1;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("/conf-service/bank/%s", ACCOUNT_ONE))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));


    }

    @Test
    void shouldGetValidBankAccountButNoJwtToken() {
        var expected = account1;

        given()
                .when()
                .get(format("/conf-service/bank/%s", ACCOUNT_ONE))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class)
    void shouldGetBankAccountButNotFound(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("/conf-service/bank/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(BankAccountNotFound.class,
                () -> service.getBankAllAccounts(NOT_FND)
        );

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldAddNewBankAccount(Role role) {
        setup(role);
        var expected = account2;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(account2)
                .contentType(APP_JSON)
                .when()
                .post("/conf-service/bank")
                .then()
                .and()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldAddNewBankAccountButNoJwtToken() {
        var expected = account2;

        given()
                .contentType(APP_JSON)
                .body(account2)
                .contentType(APP_JSON)
                .when()
                .post("/conf-service/bank")
                .then()
                .and()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldAddNewBankAccountButNotAdmin(Role role) {
        setup(role);
        var expected = account2;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(account2)
                .contentType(APP_JSON)
                .when()
                .post("/conf-service/bank")
                .then()
                .and()
                .statusCode(FORBIDDEN);
    }
}