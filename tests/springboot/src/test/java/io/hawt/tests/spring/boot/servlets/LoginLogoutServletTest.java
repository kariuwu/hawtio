package io.hawt.tests.spring.boot.servlets;

import io.hawt.tests.spring.boot.setup.SpringBootTestParent;
import io.restassured.RestAssured;
import io.restassured.internal.assertion.CookieMatcher;
import io.restassured.matcher.DetailedCookieMatcher;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringEndsWith;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static io.hawt.tests.utils.setup.LoginLogout.getUrlFromParameters;

public class LoginLogoutServletTest extends SpringBootTestParent {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI= getUrlFromParameters();}

    @Test
    public void  testLogin() {
        String cookie = RestAssured.given().body("{\"username\": \"hawtio\",\"password\": \"hawtio\"}").post("/auth/login").header("Set-Cookie");
        RestAssured.given().cookie(cookie).get("/user/*").then().log().all().statusCode(200);
    }

    @Test
    public void testLogout() {
        String cookie = RestAssured.given().body("{\"username\": \"hawtio\",\"password\": \"hawtio\"}").post("/auth/login").header("Set-Cookie");
        RestAssured.given().cookie(cookie).get("/user/*").then().log().all().statusCode(200);
        cookie = RestAssured.given().cookie(cookie).get("/auth/logout").then().log().all().extract().header("Set-Cookie");
        RestAssured.given().cookie(cookie).then().log().all();
    }


}
