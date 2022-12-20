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
public class AnalyticControllerTest extends TestBase {

	static int ID = -1;

	@Title("Get analytics cloud-wise-spend")
	@Test
	public void test001() {
		SerenityRest.rest().given().when().get("/analytics/cloud-wise-spend").then().log().all().statusCode(200);
	}

//	@Title("Get analytics sla-central")
//	@Test
//	public void test002() {
//		SerenityRest.rest().given().when().get("/analytics/sla-central").then().log().all().statusCode(200);
//	}

}
