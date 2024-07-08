package com.store.rest.controllers;

import com.store.model.security.Role;
import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.AUTHORIZATION;
import static com.store.util.constants.TestConstants.BEARER;
import static com.store.util.constants.TestConstants.FORBIDDEN;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.UserTestConstants.USER1;
import static com.store.util.constants.classes.UserTestConstants.USERNAME1;
import static com.store.util.constants.classes.UserTestConstants.registerDto1;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.emptyString;

class AdminControllerTest extends AbstractControllerTest {

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldGetValidUser(Role role) {
        setup(role);

        var e = given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("/admin/get/%s", USERNAME1))
                .then()
                .statusCode(OK)
                .body("fullName", equalTo(USER1.getFullName()))
                .body("email", equalTo(USER1.getUsername()));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldGetValidUserButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("/admin/get/%s", USERNAME1))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldRegisterNewUser(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(registerDto1())
                .when()
                .post("/admin/signup")
                .then()
                .statusCode(OK)
                .body("fullName", equalTo(registerDto1().getFullName()))
                .body("email", equalTo(registerDto1().getEmail()));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldRegisterNewUserButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(registerDto1())
                .when()
                .post("/admin/signup")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldDeleteUser(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .delete(format("/admin/delete/%s", USERNAME1))
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldDeleteUserButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .delete(format("/admin/delete/%s", USERNAME1))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldUpdateUser(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(registerDto1())
                .when()
                .patch(format("/admin/update/%s", USERNAME1))
                .then()
                .statusCode(OK);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldUpdateUserButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(registerDto1())
                .when()
                .patch(format("/admin/update/%s", USERNAME1))
                .then()
                .statusCode(FORBIDDEN);
    }

}
