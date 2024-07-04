package com.store.rest.controllers;

import com.store.dao.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.ItemTestConstants.PRODUCT_CHEFIR;
import static com.store.util.constants.classes.ItemTestConstants.prodRequest;
import static com.store.util.constants.classes.ItemTestConstants.product2;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ProductControllerTest extends ControllerTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void getAllProducts() {
        var expected = repository.findAll();

        given()
                .when()
                .get("/conf-service/products")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void getAllProductsByName() {
        var expected = repository.findAllByName(PRODUCT_CHEFIR);

        given()
                .when()
                .get("/conf-service/products/" + PRODUCT_CHEFIR)
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void addValidProduct() {
        var expected = product2;

        given()
                .contentType(APP_JSON)
                .body(prodRequest)
                .when()
                .post("/conf-service/products")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(product2)));
    }
}