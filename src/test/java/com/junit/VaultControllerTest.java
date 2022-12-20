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
public class VaultControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"cloudType\": \"aws\",\r\n"
			+ "    \"accountId\": \"897373451\",\r\n"
			+ "    \"accessKey\": \"AKIAZSLS3RLMX72WGAMX\",\r\n"
			+ "    \"secretKey\": \"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\",\r\n"
			+ "    \"region\": \"us-east-1\"\r\n"
			+ "}";

	String updateBody = "{\r\n"
			+ "    \"id\":"+ ID +",\r\n"
			+ "    \"cloudType\": \"aws\",\r\n"
			+ "    \"accountId\": \"1234\",\r\n"
			+ "    \"accessKey\": \"qazxsw\",\r\n"
			+ "    \"secretKey\": \"edcvfrtgbnhy\",\r\n"
			+ "    \"region\": \"us-east-1\"\r\n"
			+ "}";

	@Title("Add new vault data")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/vault").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

		System.out.println("This is returned data" + ID);
	}

	@Title("Update vault")
	@Test
	public void test002() {

		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/vault").then()
				.log().all().statusCode(200);
	}

	@Title("Search vault By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/vault/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all vault data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/vault").then().statusCode(200);
	}

	@Title("Delete vault data")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/vault/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
