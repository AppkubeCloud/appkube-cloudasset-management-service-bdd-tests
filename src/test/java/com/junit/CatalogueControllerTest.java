package com.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class CatalogueControllerTest extends TestBase {

	static int ID = -1;

	String body ="{\r\n"
			+ "    \"details\": {\r\n"
			+ "        \"name\":\"demo\"\r\n"
			+ "    }\r\n"
			+ "}";
	
	String updateBody ="{\r\n"
			+ "    \"id\":" + ID + ",\r\n"
			+ "    \"name\": {\r\n"
			+ "        \"dev\":\"demo\",\r\n"
			+ "        \"sec\":\"demo\",\r\n"
			+ "        \"ops\":\"demo\"\r\n"
			+ "    }\r\n"
			+ "}";


	@Title("Add new catalogue")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/catalogue").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");
	}

	@Title("Update catalogue")
	@Test
	public void test002() {

		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/catalogue").then()
				.log().all().statusCode(200);
	}

	@Title("Search catalogue By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/catalogue/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all catalogue")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/catalogue").then().statusCode(200);
	}

	@Title("Delete catalogue")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/catalogue/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
