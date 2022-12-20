package com.junit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.testbase.TestBase;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwsRegionControllerTest extends TestBase {

	static int ID = -1;

	@Title("Get aws region")
	@Test
	public void test001() {
		SerenityRest.rest().given().when().get("/getAwsRegions").then().log().all().statusCode(200);
	}
}
