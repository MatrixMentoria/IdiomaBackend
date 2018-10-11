package br.com.matrix.idioma.modelRestAssured;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;

import br.com.matrix.idioma.model.User;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.bytebuddy.utility.RandomString;

 
public class UserRestAssured {
	String idTeste;
	
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://localhost:8080/";
	}
	
	@Test
	public void postResquestUserWhenBody() {
		User user = new User();
		user.setEmail("mario@unicarioca.edu.br");
		user.setLogin(RandomString.make(5));
		user.setName("Mario");
		user.setPassword("123456");
		
		Response response = RestAssured.given().
		contentType("application/json").
		accept("application/json").		 
		body(user).
		when().
		post("/user").
		then().
		statusCode(201). 
		contentType("application/json").
		extract().
		response();
		
		String id = response.jsonPath().getString("id");
		idTeste = id;
		assertNotNull(id);
}
		
	
	
	
	@Test
	public void getResquestUserWhenBody() {
		RestAssured.given().
		when().
			get("/user/2").
			then().statusCode(200)
			.body("email", containsString("mario@unicarioca.edu.br"))
			.body("login", containsString("mario"))
			.body("name", containsString("Mario"))
			.body("password", containsString("123456"));			
	}
	
	@Test
	public void getResquestUser(){		
		RestAssured.given().
		when().
			get("/user/2").
		then().
			statusCode(200).
			body("id", is(2)).
			body("login", notNullValue()).
			body("password", notNullValue()).
			body("email", notNullValue()).
			body("name", notNullValue());										
	}
	
	@Test
	public void deleteUser() {
		User user = new User();
		user.setEmail("mario@unicarioca.edu.br");
		user.setLogin("mario8885");
		user.setName("Mario");
		user.setPassword("123456");
		
		Response response = RestAssured.given().
		contentType("application/json").
		accept("application/json").		 
		body(user).
		when().
		post("/user").
		then().
		statusCode(201). 
		contentType("application/json").
		extract().
		response();
		
		Long id = response.jsonPath().getLong("id");
		
		RestAssured.given().pathParam("id", id).
		when().delete("/user/{id}").then().statusCode(200);
		
		
	}
	@Test 
	public void getResquest() {
		RestAssured.given().
		when().get("/user/6565656").
		then().
		statusCode(404).
		body("detail",containsString("Usuário não existe"));
		
		
	}

	
	
}
