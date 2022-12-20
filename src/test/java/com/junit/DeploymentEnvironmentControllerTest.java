package com.junit;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testbase.TestBase;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeploymentEnvironmentControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"name\": \"Test\",\r\n"
			+ "    \"description\": \"Test\",\r\n"
			+ "    \"status\": \"Active\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\"\r\n"
			+ "}";

	String updateBody = "{\r\n"
			+ "    \"id\": " + ID + ",\r\n"
			+ "    \"name\": \"Production\",\r\n"
			+ "    \"description\": \"Production server\",\r\n"
			+ "    \"status\": \"Active\",\r\n"
			+ "    \"createdOn\": \"2022-04-10T17:10:33Z\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\"\r\n"
			+ "}";

	@Title("Add new service account")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/deployment-environment").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

		System.out.println("This is returned data" + ID);
	}

	@Title("Update deployment-environment")
	@Test
	public void test002() {

		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/deployment-environment").then()
				.log().all().statusCode(200);
	}

	@Title("Search deployment-environment By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/deployment-environment/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all deployment-environment data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/deployment-environment").then().statusCode(200);
	}

	@Title("Delete service account")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/deployment-environment/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
