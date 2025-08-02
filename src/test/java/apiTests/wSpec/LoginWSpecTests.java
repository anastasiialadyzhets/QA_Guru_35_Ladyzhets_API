package apiTests.wSpec;

import apiTests.lombok.LoginRequestModel;
import apiTests.lombok.LoginResponseModel;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static apiTests.specifications.LoginSpecification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;


public class LoginWSpecTests {
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
    }
    @Test
    void successfulLoginTest() {
        LoginRequestModel authData = new LoginRequestModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Логин под пользователем " + authData.getEmail(), () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse200Spec)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка токена на соответсвие", () -> {
            Assertions.assertNotNull(response.getToken());
            matchesPattern("^[A-Za-z0-9]{17}").matches(response.getToken());
        });
    }
    @Test
    void unsuccessfulLogin400Test() {
        LoginRequestModel authData = new LoginRequestModel();

        LoginResponseModel response = step("Логин без пользователя ", () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse400BadRequestSpec)
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

        LoginResponseModel response = step("Логин незарегистрированного пользователя ", () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse400BadRequestSpec)
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

        LoginResponseModel response = step("Логин без пароля " , () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse400BadRequestSpec)
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

        LoginResponseModel response = step("Логин без email ", () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse400BadRequestSpec)
                .extract().body().as(LoginResponseModel.class));

        step("Проверка текста ошибки ", () -> {
            Assertions.assertNotNull(response.getError());
            Assertions.assertEquals("Missing email or username",response.getError());
        });
    }
    @Test
    void wrongBodyTest() {
        String authData = "%}";

        step("Логин невалидный контракт ", () -> given(loginRequestSpec)
                .body(authData)

                .when()
                .post()

                .then()
                .spec(loginResponse400BadRequestSpec));
    }

    @Test
    void unsuccessfulLogin415Test() {
        step("Логин пустой", () ->
                given(loginRequestNoContentSpec)

                .post()

                .then()
                .spec(loginResponse415UnsupportedMTSpec));
    }
}
