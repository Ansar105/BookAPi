package com.restapi.test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restapi.base.Base;
import com.restapi.endpoints.Routes;
import com.restapi.util.TestUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BookTest extends Base {
	 	public static int bookId;
	 	
	 	String jsonPayload = TestUtil.readJsonFromFile("createBook.json");
	 	 
	    @Test(priority = 1, dependsOnMethods = { "com.restapi.test.AccountsTest.signUpUser",
	            "com.restapi.test.AccountsTest.verifySignUpUserAndGenerateToken" }, description = "Add a new book")
	    public void addBooksTest() {
	        Response response = given()
	                .header("Authorization", "Bearer " + AccountsTest.token)
	                .log().all() // Log the request details
	                .body(jsonPayload)
	                .when()
	                .post(Routes.ADD_NEW_BOOK)
	                .then().log().all() // Log the response details
	                .statusCode(200).extract().response();
	        bookId = response.jsonPath().getInt("id");
	        Assert.assertNotNull(bookId, "Book ID should not be null Value");

	    }

	    @Test(priority = 2, dependsOnMethods = "addBooksTest", description = "Get book by ID")
	    public void getBookByIdTest() {
	        Response response = given()
	                .header("Authorization", "Bearer " + AccountsTest.token)
	                // .pathParam("book_id", bookId)
	                .log().all() // Log the request details
	                .when()
	                .get(String.format(Routes.GET_BOOK_ID, bookId))
	                .then()
	                .log().all() // Log the response details
	                .statusCode(200)
	                .extract().response();
	        Assert.assertEquals(response.jsonPath().getString("name"),book.getName(),
	                "Book name should be match");

	    }

	    @Test(priority = 3, dependsOnMethods = "addBooksTest", description = "Update book by ID")
	    public void updateBookByIdTest() {
	    	 String updatePayload = TestUtil.readJsonFromFile("updateBook.json");
	        Response response = given()
	                .header("Authorization", "Bearer " + AccountsTest.token)
	                .body(updatePayload)
	                .log().all() // Log the request details
	                .when()
	                .put(String.format(Routes.GET_BOOK_ID, bookId))
	                .then()
	                .log().all() // Log the response details
	                .statusCode(200)
	                .extract().response();
	        JsonPath expectedJson = new JsonPath(updatePayload);
	        JsonPath actualJson = new JsonPath(response.asString());
	        Assert.assertEquals(actualJson.getString("name"), expectedJson.getString("name"),
	                "Book name should match after updated");

	    }

	    @Test(priority = 4, dependsOnMethods = "addBooksTest", description = "Get all books")
	    public void getAllBooks() {
	        Response response = given()
	                .header("Authorization", "Bearer " + AccountsTest.token)
	                .log().all() // Log the request details
	                .when()
	                .get(Routes.GET_ALL_BOOKS)
	                .then()
	                .log().all() // Log the response details
	                .statusCode(200)
	                .extract().response();

	        Assert.assertTrue(response.jsonPath().getList("books").size() > 0, "Books list should not be empty");

	    }

	    @Test(priority = 5, dependsOnMethods = "addBooksTest", description = "Delete book by ID")
	    public void deleteBookByIdTest() {
	        Response response = given()
	                .header("Authorization", "Bearer " + AccountsTest.token)
	                .log().all() // Log the request details
	                .when()
	                .delete(String.format(Routes.GET_BOOK_ID, bookId))
	                .then()
	                .log().all() // Log the response details
	                .statusCode(200)
	                .extract().response();
	        Assert.assertTrue(response.jsonPath().getString("message").contains("Book deleted successfully"),
	                "book deleted message should match");

	    }

}
