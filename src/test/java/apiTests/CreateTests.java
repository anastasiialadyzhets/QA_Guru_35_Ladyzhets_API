package apiTests;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Link;
import io.restassured.RestAssured;
import apiTests.lombok.CreateRequestModel;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.Allure.label;



public class CreateTests {
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    private final String userCreateSchema = "createUserResponse-schema.json";
    @Test
    @Description("Успешное создание пользователя со всеми полями")
    void successfulCreateTest(){
        Allure.label("epic","tetest1");
        Allure.label("feature","test/asas/asdsdsd");
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("leader");

        step("Создать пользователя со всеми полями", () -> {
            given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/user")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(userCreateSchema))
                .body("name", is("morpheus"))
                .body("job", is("leader"));
        });
    }
    @Test
    @Description("Успешное создание пользователя с пустыми полями")
    void successfulEmptyCreateTest() {
        String requestData = "{}";

        step("Создать пользователя с пустыми полями", () -> {
            given()
                    .body(requestData)
                    .header("x-api-key", "reqres-free-v1")
                    .contentType(JSON)
                    .log().uri()

                    .when()
                    .post("/user")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
                    .body(matchesJsonSchemaInClasspath(userCreateSchema));
        });
    }

    @Test
    @Description("Успешное создание пользователя со случайными полями")
    void successfulCreateCustomParametersTest() {
        CreateRequestModel requestData = new CreateRequestModel();

        requestData.setId("kvakva");
        requestData.setId2("kvakva2");
        requestData.setId3("kvakva3");
        requestData.setId4("kvakva4");
        requestData.setId5("kvakva5");

        step("Создать пользователя со случайными полями", () -> {
            given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/user")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(userCreateSchema));
        });
    }
}