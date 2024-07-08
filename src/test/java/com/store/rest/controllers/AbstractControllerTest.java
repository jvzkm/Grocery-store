package com.store.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dao.UserRepository;
import com.store.model.security.Role;
import com.store.service.impl.JwtService;
import com.store.util.mapper.JsonUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
public abstract class AbstractControllerTest {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    protected String jwtToken;


    protected void setup(Role role) {
        var username = userRepository.findFirstByRole(role).orElseThrow().getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        jwtToken = jwtService.generateToken(new HashMap<>(), userDetails);
    }

    @AfterEach
    void tearDown() {
        jwtToken = null;
    }


    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest")
    );

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @BeforeAll
    static void setUpMapper() {
        JsonUtil.setMapper(new ObjectMapper());
    }



    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}


