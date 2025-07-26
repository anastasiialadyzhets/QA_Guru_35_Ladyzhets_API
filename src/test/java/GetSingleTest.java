import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    private static final Gson gson = new Gson();
    @BeforeAll
    static void setupConfiguration(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    @Description("Запрос существующего пользователя")
    void successfulGetSingleUserTest() {
        String expectedU="{ \"data\": { \"id\": 2, \"email\": \"janet.weaver@reqres.in\", \"first_name\": \"Janet\", " +
                "\"last_name\": \"Weaver\", \"avatar\":" +
                " \"https://reqres.in/img/faces/2-image.jpg\" }, " +
                "\"support\": { \"url\": \"https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral\"," +
                " \"text\": \"Tired of writing endless social media content? Let Content Caddy generate it for you.\" } }";
        JsonObject expectedUser= gson.fromJson(expectedU, JsonObject.class);

        given()
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .get("/user/"+userIdExist)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(userIdExist))
                .body("data.email", is(expectedUser.get("data.email")))
                .body("data.first_name", is(expectedUser.get("data.first_name")))
                .body("data.last_name", is(expectedUser.get("data.last_name")))
                .body("data.avatar", is(expectedUser.get("data.avatar")));
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
