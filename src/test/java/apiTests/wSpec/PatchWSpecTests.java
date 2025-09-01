package apiTests.wSpec;

import apiTests.lombok.CreateRequestModel;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static apiTests.specifications.PatchSpecification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PatchWSpecTests {
    @BeforeEach
    public void setLabels(){
        Allure.label("epic","tetest666");
        Allure.label("feature","test/asas/asdsdsd666");
    }

    private final String userPatchSchema = "patchUserResponse-schema.json";
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
    }
    @Test
    @Description("Обновление основных полей")
    void successfulPatchFullTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("zion resident");

        step("Обновление всех основных полей пользователя с id "+userId, () -> {
            given(patchRequestSpec)
                    .body(requestData)

                    .when()
                    .patch("/" + userId)

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(userPatchSchema));
        });
    }

    @Test
    @Description("Обновление только поля Job")
    void successfulPatchOnlyJobFieldTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setJob("zion resident");

        step("Обновление поля Job для пользователя с id "+userId, () -> {
            given(patchRequestSpec)
                    .body(requestData)

                    .when()
                    .patch("/user/" + userId)

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath(userPatchSchema));
        });
    }
    @Test
    @Description("Обновление только поля Name")
    void successfulPatchOnlyNameFieldTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");

        step("Обновление поля Name для пользователя с id "+userId, () -> {
            given(patchRequestSpec)
                    .body(requestData)

                    .when()
                    .patch("/user/" + userId)

                    .then()
                    .spec(patchResponse200Spec)
                    .body(matchesJsonSchemaInClasspath(userPatchSchema));
        });
    }

    @Test
    @Description("Обновление случайных полей")
    void successfulPatchCustomFieldsTest() {
        int userId= new Random().nextInt(0,101);
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setId3("kvakva3");
        requestData.setId4("kvakva4");
        requestData.setId5("kvakva5");

        step("Обновление случайных полей для пользователя с id "+userId, () -> {
            given(patchRequestSpec)
                    .body(requestData)

                    .when()
                    .patch("/user/" + userId)

                    .then()
                    .spec(patchResponse200Spec)
                    .body(matchesJsonSchemaInClasspath(userPatchSchema));
        });
    }
}
