package com.store.rest.controllers;

import com.store.exceptions.WorkerNotFoundException;
import com.store.model.mapper.SaleMapper;
import com.store.model.mapper.WorkerMapper;
import com.store.service.SaleService;
import com.store.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.store.util.constants.TestConstants.APP_JSON;
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

class WorkerControllerTest extends ControllerTest {

    @Autowired
    private WorkerService workerService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private WorkerMapper mapper;
    @Autowired
    private SaleMapper saleMapper;

    @Test
    void getAllWorkers() {
        var expected = workerService.getWorkers()
                .stream()
                .map(mapper::workerToWorkerResponseDto)
                .toList();

        given()
                .when()
                .get("/conf-service/workers")
                .then()
                .body(equalTo(writeValue(expected)));
    }

    @Test
    void shouldGetWorker() {
        var expected = mapper.workerToWorkerResponseDto(worker3);

        given()
                .when()
                .get("/conf-service/workers/" + WORKER_2)
                .then()
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldGetWorkerButNotFound() {
        given()
                .when()
                .get("conf-service/workers/" + NOT_FND)
                .then()
                .statusCode(NOT_FOUND);

        assertThrows(WorkerNotFoundException.class,
                () -> workerService.getWorkerById(NOT_FND)
        );
    }

    @Test
    void shouldCreateNewWorker() {
        var newWorker = writeValue(mapper.workerToWorkerResponseDto(worker2));
        given()
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .post("conf-service/workers")
                .then()
                .statusCode(OK)
                .body(equalTo(newWorker));
    }

    @Test
    void shouldCreateSale() {
        var expected = saleResponseDto;

        given()
                .contentType(APP_JSON)
                .body(saleRequest1)
                .when()
                .post("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(OK)
                .body(equalTo(writeValue(expected)));

    }

    @Test
    void shouldUpdate() {
        given()
                .contentType(APP_JSON)
                .body(requestDto1)
                .when()
                .put("conf-service/workers/" + VALID_WORKER)
                .then()
                .statusCode(OK)
                .body(emptyString());
    }

    @Test
    void shouldUpdateWorkerButNotFound() {
        given()
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