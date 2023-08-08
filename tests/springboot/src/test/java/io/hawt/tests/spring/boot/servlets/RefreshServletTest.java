package io.hawt.tests.spring.boot.servlets;

import io.hawt.tests.spring.boot.setup.SpringBootTestParent;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.hawt.tests.utils.setup.LoginLogout.getUrlFromParameters;

public class RefreshServletTest extends SpringBootTestParent {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI= getUrlFromParameters();}

    @Test
    public void  testRefresh() {
        String cookie = RestAssured.given().body("{\"username\":\"hawtio\",\"password\":\"hawtio\"}").post("/auth/login").header("Set-Cookie");
        RestAssured.given().cookie(cookie).get("/refresh").then().statusCode(200);}

}
