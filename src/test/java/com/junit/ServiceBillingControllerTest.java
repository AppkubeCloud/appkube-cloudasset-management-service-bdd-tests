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
public class ServiceBillingControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"periodFrom\": \"2022-04-08\",\r\n"
			+ "    \"periodTo\": \"2022-04-07\",\r\n"
			+ "    \"dueDate\": \"2022-04-08\",\r\n"
			+ "    \"amount\": 91262.0,\r\n"
			+ "    \"description\": \"UNPAID\",\r\n"
			+ "    \"status\": \"UNPAID\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\",\r\n"
			+ "    \"product\": {\"id\":452},\r\n"
			+ "    \"services\": {\"id\":504},\r\n"
			+ "    \"department\": {\"id\":303}\r\n"
			+ "}";

	String updateBody = "{\r\n"
			+ "    \"id\": "+ ID +",\r\n"
			+ "    \"periodFrom\": \"2022-04-08\",\r\n"
			+ "    \"periodTo\": \"2022-04-07\",\r\n"
			+ "    \"dueDate\": \"2022-04-08\",\r\n"
			+ "    \"amount\": 3000.94,\r\n"
			+ "    \"description\": \"Test\",\r\n"
			+ "    \"status\": \"UNPAID\",\r\n"
			+ "    \"createdOn\": \"2022-04-10T21:12:28.400905700Z\",\r\n"
			+ "    \"updatedOn\": \"2022-04-10T21:12:28.400905700Z\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\",\r\n"
			+ "    \"product\": {\r\n"
			+ "        \"id\": 1\r\n"
			+ "    },\r\n"
			+ "    \"services\": {\r\n"
			+ "        \"id\": 2\r\n"
			+ "    },\r\n"
			+ "    \"department\": {\r\n"
			+ "        \"id\": 3\r\n"
			+ "    }\r\n"
			+ "}";

	@Title("Add new service account")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/service-billing").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

		System.out.println("This is returned data" + ID);
	}

//	@Title("Update service-billing")
//	@Test
//	public void test002() {
//
//		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/service-billing").then()
//				.log().all().statusCode(200);
//	}

	@Title("Search service-billing By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/service-billing/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all service-billing data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/service-billing").then().statusCode(200);
	}

	@Title("Delete service account")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/service-billing/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
