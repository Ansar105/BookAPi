package com.restapi.test;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restapi.base.Base;
import com.restapi.endpoints.Routes;

import io.restassured.response.Response;

public class HealthTest extends Base {
	
	
	 @Test(priority = 1, groups = "healthCheck", description = "Health check to check if server is up")
	    public void healthCheck() {
	        Response response = given()
	                .log().all() // Log the request details
	                .when()
	                .get(Routes.HEALTH_CHECK)
	                .then()
	                .log().all() // Log the response details
	                .statusCode(200)
	                .extract()
	                .response();

	        String status = response.jsonPath().getString("status");
	        Assert.assertEquals(status, "up", "Health check status mismatched");
	    }

}
