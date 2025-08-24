package com.restapi.endpoints;
import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookEndpoints {
	
	protected Response get(String endpoint) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    protected Response post(String endpoint, Object payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    protected Response put(String endpoint, Object payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    protected Response delete(String endpoint) {
        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

}
