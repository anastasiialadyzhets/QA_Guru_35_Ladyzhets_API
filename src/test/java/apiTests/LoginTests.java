package apiTests;

import apiTests.lombok.LoginResponseModel;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import apiTests.lombok.LoginRequestModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;


public class LoginTests {
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    void successfulLoginTest() {
        LoginRequestModel authData = new LoginRequestModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Логин под пользователем " + authData.getEmail(), () -> given()
                .body(authData)
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка токена на соответсвие", () -> {
            Assertions.assertNotNull(response.getToken());

            matchesPattern("^[A-Za-z0-9]{17}").matches(response.getToken());
        });
    }
    @Test
    void unsuccessfulLogin400Test() {
        LoginRequestModel authData = new LoginRequestModel();

        LoginResponseModel response = step("Логин без пользователя ", () -> given()
                .body(authData)
                .header("x-api-key", "reqres-free-v1")
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка текста ошибки ", () -> {
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals("Missing email or username",response.getError());
        });
    }

    @Test
    void userNotFoundTest() {
        LoginRequestModel authData = new LoginRequestModel();
        authData.setEmail("eveasdas.holt@reqres.in");
        authData.setPassword("cda");

        LoginResponseModel response = step("Логин незарегистрированного пользователя ", () -> given()
                .body(authData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка текста ошибки ", () -> {
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals("user not found",response.getError());
        });
    }

    @Test
    void missingPasswordTest() {
        LoginRequestModel authData = new LoginRequestModel();
        authData.setEmail("eveasdas.holt@reqres.in");

        LoginResponseModel response = step("Логин без пароля " , () -> given()
                .body(authData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка текста ошибки ", () -> {
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals("Missing password",response.getError());
        });
    }


    @Test
    void missingLoginTest() {
        LoginRequestModel authData = new LoginRequestModel();
        authData.setPassword("cda");

        LoginResponseModel response = step("Логин без email ", () -> given()
                .body(authData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка текста ошибки ", () -> {
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals("Missing email or username",response.getError());
        });
    }
    @Test
    void wrongBodyTest() {
        String authData = "%}";

        step("Логин невалидный контракт ", () ->
                given()
                .body(authData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400));
    }

    @Test
    void unsuccessfulLogin415Test() {
        step("Логин пустой", () ->
                given()
                .header("x-api-key","reqres-free-v1")
                .log().uri()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415));
    }
}
