package com.codebykavindu.bookstore.catalog;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

/**
 * @author Kavindu Perera
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContainersConfig.class)
public abstract class AbstractIntegrationTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
