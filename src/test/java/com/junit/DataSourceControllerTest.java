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
public class DataSourceControllerTest extends TestBase {

	static int ID = -1;

	String body = "{\r\n"
			+ "    \"name\":\"demo\",\r\n"
			+ "    \"description\":\"demo\"\r\n"
			+ "}";

//	String updateBody = "{\r\n"
//			+ "    \"id\":"+  ID +",\r\n"
//			+ "    \"name\":\"demo\",\r\n"
//			+ "    \"description\":\"demo\"\r\n"
//			+ "}";

	@Title("Add new service account")
	@Test
	public void test001() throws JsonMappingException, JsonProcessingException {

		Object returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON).log().all().body(body)
				.post("/data-source").then().log().all().statusCode(200).extract().path("");

		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = oMapper.convertValue(returnData, Map.class);
		ID = (int) map.get("id");

		System.out.println("This is returned data" + ID);
	}

//	@Title("Update data-source")
//	@Test
//	public void test002() {
//
//		SerenityRest.rest().given().when().contentType(ContentType.JSON).body(updateBody).put("/data-source").then()
//				.log().all().statusCode(200);
//	}

	@Title("Search data-source By id")
	@Test
	public void test003() {
		SerenityRest.rest().given().when().get("/data-source/" + ID).then().log().all()
				.statusCode(200);
	}

	@Title("Get all data-source data")
	@Test
	public void test004() {
		SerenityRest.rest().given().when().get("/data-source").then().statusCode(200);
	}

	@Title("Delete service account")
	@Test
	public void test005() {
		ExtractableResponse<Response> returnData = SerenityRest.rest().given().when().contentType(ContentType.JSON)
				.log().all().delete("/data-source/" + ID).then().log().all().extract();

		assertNotNull(returnData);
	}

}
