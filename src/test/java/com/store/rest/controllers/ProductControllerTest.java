package com.store.rest.controllers;

import com.store.dao.ProductRepository;
import com.store.model.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.AUTHORIZATION;
import static com.store.util.constants.TestConstants.BEARER;
import static com.store.util.constants.TestConstants.FORBIDDEN;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.ItemTestConstants.PRODUCT_CHEFIR;
import static com.store.util.constants.classes.ItemTestConstants.prodRequest;
import static com.store.util.constants.classes.ItemTestConstants.product2;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ProductControllerTest extends AbstractControllerTest {

    @Autowired
    private ProductRepository repository;

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void getAllProducts(Role role) {
        setup(role);
        var expected = repository.findAll();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/products")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllProductsButNoAuthority(Role role) {
        setup(role);
        var expected = repository.findAll();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/products")
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void getAllProductsButNoJwtToken() {
        given()
                .when()
                .get("/conf-service/products")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void getAllProductsByName(Role role) {
        setup(role);
        var expected = repository.findAllByName(PRODUCT_CHEFIR);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/products/" + PRODUCT_CHEFIR)
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllProductsByNameButNoAuthority(Role role) {
        setup(role);
        var expected = repository.findAllByName(PRODUCT_CHEFIR);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/products/" + PRODUCT_CHEFIR)
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void getAllProductsByNameButNoJwtToken() {
        given()
                .when()
                .get("/conf-service/products/" + PRODUCT_CHEFIR)
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void addValidProduct(Role role) {
        setup(role);
        var expected = product2;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(prodRequest)
                .when()
                .post("/conf-service/products")
                .then()
                .statusCode(OK)
                .body("name",equalTo(product2.getName()))
                .body("category",equalTo(product2.getCategory().name()));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"}, mode = EnumSource.Mode.EXCLUDE)
    void addValidProductButNoAuthority(Role role) {
        setup(role);
        var expected = product2;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(prodRequest)
                .when()
                .post("/conf-service/products")
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void addValidProductButNoJwtToken() {
        given()
                .contentType(APP_JSON)
                .body(prodRequest)
                .when()
                .post("/conf-service/products")
                .then()
                .statusCode(FORBIDDEN);
    }
}