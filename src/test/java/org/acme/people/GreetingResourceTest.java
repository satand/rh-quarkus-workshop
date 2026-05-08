package org.acme.people;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from Quarkus REST"));
    }

    @Test
    public void testGreetingEndpointWithDefaultLanguage() {
        given()
          .pathParam("name", "quarkus")
          .when().get("/hello/greeting/{name}")
          .then()
            .statusCode(200)
            .body(is("hello quarkus"))
            .header(HttpHeaders.CONTENT_TYPE, containsString("text/plain"));
    }

    @Test
    public void testGreetingEndpointWithSpecifLanguage() {
        given()
          .pathParam("name", "quarkus")
          .queryParam("locale", "it")
          .when().get("/hello/greeting/{name}")
          .then()
            .statusCode(200)
            .body(is("ciao quarkus"))
            .header(HttpHeaders.CONTENT_TYPE, containsString("text/plain"));
    }

    @Test
    public void testGreetingEndpointWithUnknownLanguage() {
        given()
          .pathParam("name", "quarkus")
          .queryParam("locale", "et")
          .when().get("/hello/greeting/{name}")
          .then()
            .statusCode(200)
            .body(is("hello quarkus"))
            .header(HttpHeaders.CONTENT_TYPE, containsString("text/plain"));
    }
}