package apiTests.wSpec;

import apiTests.lombok.CreateRequestModel;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static apiTests.specifications.CreateUserSpecification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;


public class CreateWSpecTests {
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
    }

    private final String userCreateSchema = "createUserResponse-schema.json";
    @Test
    @Description("Успешное создание пользователя со всеми полями")
    void successfulCreateTest(){
        CreateRequestModel requestData = new CreateRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("leader");

        step("Создать пользователя со всеми полями", () -> {
            given(createUserRequestSpec)
                .body(requestData)

                .when()
                .post()

                .then()
                .spec(createUserResponse201Spec)
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
            given(createUserRequestSpec)
                    .body(requestData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponse201Spec)
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
            given(createUserRequestSpec)
                .body(requestData)

                .when()
                .post()

                .then()
                .spec(createUserResponse201Spec)
                .body(matchesJsonSchemaInClasspath(userCreateSchema));
        });
    }
}