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
public class ProductBillingControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"periodFrom\": \"2022-04-08\",\r\n"
			+ "    \"periodTo\": \"2022-04-07\",\r\n"
			+ "    \"dueDate\": \"2022-04-07\",\r\n"
			+ "    \"amount\": 378.34,\r\n"
			+ "    \"description\": \"web-enabled Nevada\",\r\n"
			+ "    \"status\": \"UNPAID\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\",\r\n"
			+ "    \"product\": {\"id\":452},\r\n"
			+ "    \"department\": {\"id\":303},\r\n"
			+ "    \"deploymentEnvironment\": {\"id\":402}\r\n"
			+ "}";

	String updateBody = "{\r\n"
			+ "    \"id\": "+ ID +",\r\n"
			+ "    \"periodFrom\": \"2022-04-08\",\r\n"
			+ "    \"periodTo\": \"2022-04-07\",\r\n"
			+ "    \"dueDate\": \"2022-05-17\",\r\n"
			+ "    \"amount\": 11223.46,\r\n"
			+ "    \"description\": \"web-enabled Nevada\",\r\n"
			+ "    \"status\": \"UNPAID\",\r\n"
			+ "    \"createdOn\": \"2022-04-11T05:38:21.333993100Z\",\r\n"
			+ "    \"updatedOn\": \"2022-04-11T05:38:21.333993100Z\",\r\n"
			+ "    \"updatedBy\": \"Postman\",\r\n"
			+ "    \"createdBy\": \"Postman\",\r\n"
			+ "    \"product\": {\r\n"
			+ "        \"id\": 1\r\n"
			+ "    },\r\n"
			+ "    \"department\": {\r\n"
			+ "        \"id\": 2\r\n"
			+ "    },\r\n"
			+ "    \"deploymentEnvironment\": {\r\n"
			+ "        \"id\": 3\r\n"
			+ "    }\r\n"
			+ "}";

	@Title("Add new service account")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/product-billing").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

	}

//	@Title("Update product-billing")
//	@Test
//	public void test002() {
//
//		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/product-billing").then()
//				.log().all().statusCode(200);
//	}

	@Title("Search product-billing By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/product-billing/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all product-billing data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/product-billing").then().statusCode(200);
	}

	@Title("Delete service account")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/product-billing/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
