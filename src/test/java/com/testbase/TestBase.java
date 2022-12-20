package com.testbase;

import org.junit.BeforeClass;

import io.restassured.RestAssured;

public class TestBase {
	public double randomVal = Math.random();
	
	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost:5057/api";
	}
}
