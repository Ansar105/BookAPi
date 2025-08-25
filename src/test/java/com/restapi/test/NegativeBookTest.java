package com.restapi.test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restapi.base.Base;
import com.restapi.endpoints.Routes;
import com.restapi.util.TestUtil;

import io.restassured.response.Response;

public class NegativeBookTest extends Base {

	@Test(dependsOnMethods = { "com.restapi.test.AccountsTest.signUpUser",
			"com.restapi.test.AccountsTest.verifySignUpUserAndGenerateToken" }, description = "Add a book with invalid token")
	public void addBookWithInvalidTokenTest() {
		String jsonPayload = TestUtil.readJsonFromFile("createBook.json");
		Response response = given().header("Authorization", "Bearer " + "invalid_token").log().all() // Log the request
																										// details
				.body(jsonPayload).when().post(Routes.ADD_NEW_BOOK).then().log().all() // Log the response details
				.statusCode(403).extract().response();
		String message = response.jsonPath().getString("detail");
		Assert.assertEquals(message, "Invalid token or expired token", "Invalid Token Mismatch");
	}

	@Test(dependsOnMethods = { "com.restapi.test.AccountsTest.signUpUser",
			"com.restapi.test.AccountsTest.verifySignUpUserAndGenerateToken" }, description = "Update book with invalid token")
	public void updateBookWithInvalidTokenTest() {
		 String updatePayload = TestUtil.readJsonFromFile("updateBook.json");
		Response response = given().header("Authorization", "Bearer " + "invalid_token").log().all() // Log the request
																										// details
				.body(updatePayload).when().put(String.format(Routes.GET_BOOK_ID, BookTest.bookId)).then().log()
				.all() // Log the response details
				.statusCode(403).extract().response();
		String message = response.jsonPath().getString("detail");
		Assert.assertEquals(message, "Invalid token or expired token", "Error message mismatch");
	}

	@Test(dependsOnMethods = { "com.restapi.test.AccountsTest.signUpUser",
			"com.restapi.test.AccountsTest.verifySignUpUserAndGenerateToken" }, description = "Delete book with invalid token")
	public void deleteBookWithInvalidTokenTest() {
		Response response = given().header("Authorization", "Bearer " + "invalid_token").log().all() // Log the request
																										// details
				.when().delete(String.format(Routes.GET_BOOK_ID, BookTest.bookId)).then().log().all() // Log the
																											// response
																											// details
				.statusCode(403).extract().response();
		String message = response.jsonPath().getString("detail");
		Assert.assertEquals(message, "Invalid token or expired token", "Error message mismatch");
	}

	@Test(dependsOnMethods = "com.restapi.test.BookTest.addBooksTest", description = "Get all books with invalid token")
	public void getAllBookWithInvalidTokenTest() {
		Response response = given().header("Authorization", "Bearer " + "invalid_token").log().all() // Log the request
																										// details
				.when().get(Routes.GET_ALL_BOOKS).then().log().all() // Log the response details
				.statusCode(403).extract().response();

		String message = response.jsonPath().getString("detail");
		Assert.assertEquals(message, "Invalid token or expired token", "Error message mismatch");
	}

	@Test(dependsOnMethods = "com.restapi.test.BookTest.addBooksTest", description = "Get book with non-existent ID")
	public void getBookWithNonExistantIdTest() {
		Response response = given().header("Authorization", "Bearer " + AccountsTest.token).log().all() // Log the
																											// request
																											// details
				.when().get(String.format(Routes.GET_BOOK_ID, "89999")).then().log().all() // Log the response
																									// details
				.statusCode(404).extract().response();
		String message = response.jsonPath().getString("detail");
		Assert.assertEquals(message, "Book not found", "Error message mismatch");
	}

	@Test(priority = 2, dependsOnMethods = "com.restapi.test.BookTest.addBooksTest", description = "Get book with invalid ID")
	public void getBookWithInvalidIdTest() {
		Response response = given().header("Authorization", "Bearer " + AccountsTest.token).log().all() // Log the
																											// request
																											// details
				.when().get(String.format(Routes.GET_BOOK_ID, "inalid_id")).then().log().all() // Log the response
																										// details
				.statusCode(422).extract().response();
		String message = response.jsonPath().getString("detail[0].msg");
		Assert.assertEquals(message, "Input should be a valid integer, unable to parse string as an integer",
				"Error message mismatch");
	}
}
