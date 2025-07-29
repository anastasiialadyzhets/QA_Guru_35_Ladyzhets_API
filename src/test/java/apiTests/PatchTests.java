package apiTests;

import apiTests.lombok.CreateRequestModel;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PatchTests {
    private final String userPatchSchema = "patchUserResponse-schema.json";
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    @Description("Обновление основных полей")
    void successfulPatchFullTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("zion resident");

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }

    @Test
    @Description("Обновление только поля Job")
    void successfulPatchOnlyJobFieldTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setJob("zion resident");

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }
    @Test
    @Description("Обновление только поля Name")
    void successfulPatchOnlyNameFieldTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }

    @Test
    @Description("Обновление случайных полей")
    void successfulPatchCustomFieldsTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setId3("kvakva3");
        requestData.setId4("kvakva4");
        requestData.setId5("kvakva5");

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }
}
