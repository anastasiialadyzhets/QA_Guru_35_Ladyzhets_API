package apiTests;

import apiTests.lombok.SingleUserResponseModel;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetSingleTest {
    private final  int userIdExist=2;
    private final  int userIdNotFound=23;
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    @Description("Запрос существующего пользователя")
    void successfulGetSingleUserTest() {
        Allure.label("epic","tetest5");
        Allure.label("feature","test/asa5s/asdsds5d");

        SingleUserResponseModel expectedUser = new SingleUserResponseModel();
        SingleUserResponseModel.Data responseDataPath=new SingleUserResponseModel.Data();
        SingleUserResponseModel.Support responseSupportPath=new SingleUserResponseModel.Support();

        responseDataPath.setId(2);
        responseDataPath.setEmail("janet.weaver@reqres.in");
        responseDataPath.setFirstName("Janet");
        responseDataPath.setLastName("Weaver");
        responseDataPath.setAvatar("https://reqres.in/img/faces/2-image.jpg");
        responseSupportPath.setText("Tired of writing endless social media content? Let Content Caddy generate it for you.");
        responseSupportPath.setUrl("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
        expectedUser.setData(responseDataPath);
        expectedUser.setSupport(responseSupportPath);

        given()
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .get("/users/"+userIdExist)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(userIdExist))
                .body("data.email", is(expectedUser.getData().getEmail()))
                .body("data.first_name", is(expectedUser.getData().getFirstName()))
                .body("data.last_name", is(expectedUser.getData().getLastName()))
                .body("data.avatar", is(expectedUser.getData().getAvatar()));
    }

    @Test
    @Description("Запрос Несуществующего пользователя")
    void unsuccessfulGetSingleUserNotExistTest() {
        given()
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .get("/api/user/"+userIdNotFound)

                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(equalTo("{}"));
    }
}
