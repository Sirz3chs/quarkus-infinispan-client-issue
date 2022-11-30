package org.acme;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ReactiveGreetingResource.class)
public class ReactiveGreetingResourceTest {

    @Test
    @Disabled("Never ends")
    public void testHelloEndpoint() {
        given()
            .when()
            .get("/foo1")
            .then()
            .statusCode(200)
            .body(is("Hello foo1"));
    }

    @Test
    public void testHello2Endpoint() {
        given()
            .when()
            .get("/foo2/2")
            .then()
            .statusCode(200)
            .body(is("Hello foo2"));
    }

    @Test
    public void testHello3Endpoint() {
        given()
            .when()
            .get("/foo3/3")
            .then()
            .statusCode(200)
            .body(is("Hello foo3"));
    }

    @Test
    public void testHello4Endpoint() {
        given()
            .when()
            .get("/foo4/4")
            .then()
            .statusCode(200)
            .body(is("Hello foo4"));
    }
}
