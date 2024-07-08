package com.store.rest.controllers;

import org.junit.jupiter.api.Test;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.CONFLICT;
import static com.store.util.constants.TestConstants.FORBIDDEN;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.UserTestConstants.USER1;
import static com.store.util.constants.classes.UserTestConstants.USER2;
import static com.store.util.constants.classes.UserTestConstants.loginDto;
import static com.store.util.constants.classes.UserTestConstants.regularUserDto;
import static com.store.util.constants.classes.UserTestConstants.unknownDto;
import static com.store.util.constants.classes.UserTestConstants.usedEmailUserDto;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class AuthenticationControllerTest extends AbstractControllerTest {

    @Test
    void shouldRegisterUser() {
        given()
                .contentType(APP_JSON)
                .when()
                .body(regularUserDto())
                .post("/auth/signup")
                .then()
                .statusCode(OK)
                .body("fullName", equalTo(USER2.getFullName()))
                .body("email", equalTo(USER2.getUsername()));

    }

    @Test
    void shouldRegisterUserButExistingEmail() {
        given()
                .contentType(APP_JSON)
                .when()
                .body(usedEmailUserDto())
                .post("/auth/signup")
                .then()
                .statusCode(CONFLICT);
    }

    @Test
    void shouldLogInUser() {
        given()
                .contentType(APP_JSON)
                .when()
                .body(loginDto)
                .post("/auth/login")
                .then()
                .statusCode(OK);

    }

    @Test
    void shouldLogInUserBadCredentials() {
        given()
                .contentType(APP_JSON)
                .when()
                .body(unknownDto)
                .post("/auth/login")
                .then()
                .statusCode(FORBIDDEN);

    }
}
