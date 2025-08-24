package com.restapi.base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.restapi.payload.Book;
import com.restapi.test.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class Base {
	
	protected Book book;
	
	@BeforeSuite
    public static void setUp() {
        String env= ConfigReader.get("env");
        if (env.equalsIgnoreCase("prod")) {
            RestAssured.baseURI = ConfigReader.get("prodUrl");
        } else {
            RestAssured.baseURI = ConfigReader.get("url");
        }
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

    }
	
 	@BeforeClass
 	public void setupData() {
 		book=new Book();
 		book.setName("Harry Potter and the Sorcerer's Stone");
 		book.setAuthor("J.K.Rowling");
 		book.setPublishedYear(1997);
 		book.setBookSummary("It introduces Harry Potter, an orphaned boy who discovers he's a wizard and is invited to attend Hogwarts School of Witchcraft and Wizardry.");
 	}
 	

}
