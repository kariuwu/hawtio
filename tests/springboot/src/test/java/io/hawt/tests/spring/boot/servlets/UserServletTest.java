package io.hawt.tests.spring.boot.servlets;


import io.hawt.tests.spring.boot.setup.SpringBootTestParent;
import io.hawt.tests.utils.setup.LoginLogout;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static io.hawt.tests.utils.setup.LoginLogout.*;

public class UserServletTest extends SpringBootTestParent {

    @BeforeClass
    public static void setup(){RestAssured.baseURI= getUrlFromParameters();}
    @Test
    public void testNotLoggedIn(){
        RestAssured.given().get("/user/*").then().statusCode(403);
        }
    @Test
    public void testLoggedIn(){
        String cookie = RestAssured.given().body("{\"username\": \"hawtio\",\"password\": \"hawtio\"}").post("/auth/login").header("Set-Cookie");
        RestAssured.given().cookie(cookie).get("/user/*").then().statusCode(200);

    }

}
