package com.store.rest.controllers;

import com.store.exceptions.ItemNotFoundException;
import com.store.model.mapper.ItemMapper;
import com.store.model.security.Role;
import com.store.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.*;
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


class ItemControllerTest extends AbstractControllerTest {

    @Autowired
    private ItemService service;
    @Autowired
    private ItemMapper mapper;

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "USER"})
    void shouldGetAllItems(Role role) {
        setup(role);
        var expected = service.getItems().stream()
                .map(mapper::itemToItemResponseDto)
                .toList();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "USER"}, mode = EnumSource.Mode.EXCLUDE)
    void shouldGetAllItemsButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void shouldGetAllItemButNoJwtToken() {
        given()
                .when()
                .get("conf-service/items")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "USER"})
    void shouldGetValidItem(Role role) {
        setup(role);

        var expected = mapper.itemToItemResponseDto(service.getItemById(ID_ONE));

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "USER"}, mode = EnumSource.Mode.EXCLUDE)
    void shouldGetValidItemButNoAuthority(Role role) {
        setup(role);

        var expected = mapper.itemToItemResponseDto(service.getItemById(ID_ONE));

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void shouldGetValidItemButNoJwtToken() {
        given()
                .when()
                .get(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "USER"})
    void shouldGetItemButNotFound(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get(format("conf-service/items/%s", NOT_FND))
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(ItemNotFoundException.class,
                () -> service.getItemById(NOT_FND)
        );
    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldAddNewItem(Role role) {
        setup(role);
        var expected = itemResponseDto1;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldAddNewItemButNotAdmin(Role role) {
        setup(role);
        var expected = itemResponseDto1;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .post("conf-service/items")
                .then()
                .statusCode(FORBIDDEN);

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldUpdateItem(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .put(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldUpdateItemButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(itemRequestDto1)
                .when()
                .put(format("conf-service/items/%s", ITEM_ONE))
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldUpdateItemButNotFound(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
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