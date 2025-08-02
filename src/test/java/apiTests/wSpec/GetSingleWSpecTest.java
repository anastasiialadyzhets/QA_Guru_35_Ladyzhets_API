package apiTests.wSpec;

import apiTests.lombok.SingleUserResponseModel;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static apiTests.specifications.GetSingleUserSpecification.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetSingleWSpecTest {
    private final  int userIdExist=2;
    private final  int userIdNotFound=23;
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
    }
    @Test
    @Description("Запрос существующего пользователя")
    void successfulGetSingleUserTest() {
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

        step("Получить информацию по пользователю с id "+userIdExist, () -> {
            given(getRequestSpec)
                    .when()
                    .get("/" + userIdExist)

                    .then()
                    .spec(getUserResponse200Spec)
                    .body("data.id", is(userIdExist))
                    .body("data.email", is(expectedUser.getData().getEmail()))
                    .body("data.first_name", is(expectedUser.getData().getFirstName()))
                    .body("data.last_name", is(expectedUser.getData().getLastName()))
                    .body("data.avatar", is(expectedUser.getData().getAvatar()));
        });
    }

    @Test
    @Description("Запрос Несуществующего пользователя")
    void unsuccessfulGetSingleUserNotExistTest() {
        step("Получить информацию по пользователю с id "+userIdExist, () -> {
            given(getRequestSpec)
                    .when()
                    .get("/" + userIdNotFound)

                    .then()
                    .spec(getUserResponse404NotFoundSpec)
                    .body(equalTo("{}"));
        });
    }
}
