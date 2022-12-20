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
public class CloudEnvironmentControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"name\": \"Test Client 2\",\r\n"
			+ "    \"description\": \"Dummy customer's AWS account. Test Client 2\",\r\n"
			+ "    \"accountId\": \"657907747545\",\r\n"
			+ "    \"accessKey\": \"AKIAZSLS3RLMTWWI2H5J\",\r\n"
			+ "    \"secretKey\": \"evHxxbX2O+qemOy9jJMmu6il9JeC5PPOZ4QdSS2I\",\r\n"
			+ "    \"region\": \"us-east-1\",\r\n"
			+ "    \"orgId\":78,\r\n"
			+ "    \"status\": \"Active\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\",\r\n"
			+ "    \"cloud\":{\r\n"
			+ "        \"id\": 251\r\n"
			+ "    },\r\n"
			+ "    \"department\":{\r\n"
			+ "        \"id\": 303\r\n"
			+ "    }\r\n"
			+ "}";

	@Title("Add new service account")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/cloud-environment").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

		System.out.println("This is returned data" + ID);
	}

	@Title("Search cloud-environment By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/cloud-environment/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all cloud-environment data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/cloud-environment").then().statusCode(200);
	}

	@Title("Delete service account")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/cloud-environment/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
