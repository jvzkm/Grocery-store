package com.store.rest.controllers;

import com.store.exceptions.ItemNotFoundException;
import com.store.model.mapper.ItemMapper;
import com.store.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.APP_JSON;
import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.NOT_FOUND;
import static com.store.util.constants.TestConstants.OK;
import static com.store.util.constants.classes.BankTestConstants.NOT_FND;
import static com.store.util.constants.classes.ItemTestConstants.ITEM_ONE;
import static com.store.util.constants.classes.ItemTestConstants.itemRequestDto1;
import static com.store.util.constants.classes.ItemTestConstants.itemResponseDto1;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ItemControllerTest extends ControllerTest {

    @Autowired
    private ItemService service;
    @Autowired
    private ItemMapper mapper;

    @Test
    void shouldGetAllItems() {
        var expected = service.getItems().stream()
                .map(mapper::itemToItemResponseDto)
                .toList();

        given()
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldGetValidItem() {

        var expected = mapper.itemToItemResponseDto(service.getItemById(ID_ONE));

        given()
                .when()
                .get(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldGetItemButNotFound() {
        given()
                .when()
                .get(format("conf-service/items/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(ItemNotFoundException.class,
                () -> service.getItemById(NOT_FND)
        );
    }

    @Test
    void shouldAddNewItem() {
        var expected = itemResponseDto1;

        given()
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldUpdateItem() {

        given()
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .put(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @Test
    void shouldUpdateItemButNotFound() {
        given()
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .put(format("conf-service/items/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(ItemNotFoundException.class,
                () -> service.getItemById(NOT_FND)
        );
    }
}