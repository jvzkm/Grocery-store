package com.store.rest.controllers;

import com.store.exceptions.WorkerNotFoundException;
import com.store.model.mapper.SaleMapper;
import com.store.model.mapper.WorkerMapper;
import com.store.model.security.Role;
import com.store.service.SaleService;
import com.store.service.WorkerService;
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
import static com.store.util.constants.classes.BankTestConstants.NOT_FND;
import static com.store.util.constants.classes.SaleTestConstants.saleRequest1;
import static com.store.util.constants.classes.SaleTestConstants.saleResponseDto;
import static com.store.util.constants.classes.WorkerTestConstants.VALID_WORKER;
import static com.store.util.constants.classes.WorkerTestConstants.WORKER_2;
import static com.store.util.constants.classes.WorkerTestConstants.requestDto1;
import static com.store.util.constants.classes.WorkerTestConstants.worker2;
import static com.store.util.constants.classes.WorkerTestConstants.worker3;
import static com.store.util.mapper.JsonUtil.writeValue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkerAbstractControllerTest extends AbstractControllerTest {

    @Autowired
    private WorkerService workerService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private WorkerMapper mapper;
    @Autowired
    private SaleMapper saleMapper;

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void getAllWorkers(Role role) {
        setup(role);
        var expected = workerService.getWorkers()
                .stream()
                .map(mapper::workerToWorkerResponseDto)
                .toList();

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/workers")
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"}, mode = EnumSource.Mode.EXCLUDE)
    void getAllWorkersButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/workers")
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void getAllWorkersButNoJwtToken() {
        given()
                .when()
                .get("/conf-service/workers")
                .then()
                .statusCode(FORBIDDEN);
    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void shouldGetWorker(Role role) {
        setup(role);
        var expected = mapper.workerToWorkerResponseDto(worker3);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/workers/" + WORKER_2)
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"}, mode = EnumSource.Mode.EXCLUDE)
    void shouldGetWorkerButNoAuthority(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("/conf-service/workers/" + WORKER_2)
                .then()
                .statusCode(FORBIDDEN);
    }

    @Test
    void shouldGetWorkerButNoJwtToken() {
        given()
                .when()
                .get("/conf-service/workers/" + WORKER_2)
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = {"ADMIN", "WORKER"})
    void shouldGetWorkerButNotFound(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .when()
                .get("conf-service/workers/" + NOT_FND)
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(WorkerNotFoundException.class,
                () -> workerService.getWorkerById(NOT_FND)
        );
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldCreateNewWorker(Role role) {
        setup(role);
        var newWorker = writeValue(mapper.workerToWorkerResponseDto(worker2));
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post("conf-service/workers")
                .then()
                .statusCode(OK)
                .body(equalTo(newWorker));
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN", mode = EnumSource.Mode.EXCLUDE)
    void shouldCreateNewWorkerButNoAuthority(Role role) {
        setup(role);
        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post("conf-service/workers")
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "WORKER")
    void shouldCreateSale(Role role) {
        setup(role);
        var expected = saleResponseDto;

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(saleRequest1)
                .when()
                .post("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "WORKER",mode = EnumSource.Mode.EXCLUDE)
    void shouldCreateSaleButNoAuthortity(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(saleRequest1)
                .when()
                .post("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(FORBIDDEN);

    }


    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldUpdate(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .put("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN",mode = EnumSource.Mode.EXCLUDE)
    void shouldUpdateButNoAuthority(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .put("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(FORBIDDEN);
    }

    @ParameterizedTest
    @EnumSource(value = Role.class, names = "ADMIN")
    void shouldUpdateWorkerButNotFound(Role role) {
        setup(role);

        given()
                .header(AUTHORIZATION, BEARER + jwtToken)
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .put("conf-service/workers/" + NOT_FND)
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(WorkerNotFoundException.class,
                () -> workerService.getWorkerById(NOT_FND)
        );
    }
}