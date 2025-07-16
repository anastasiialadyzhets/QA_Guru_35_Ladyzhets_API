import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PatchTests {
    private final String userPatchSchema = "patchUserResponse-schema.json";
    @Test
    @Description("Обновление основных полей")
    void successfulPatchFullTest() {
        String requestData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";//, \"id3\": \"kvakva3\", \"id4\": \"kvakva4\", \"id5\": \"kvakva5\"}";
        int userId= new Random().nextInt(0,101);

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }

    @Test
    @Description("Обновление только поля Job")
    void successfulPatchOnlyJobFieldTest() {
        String requestData = "{\"job\": \"zion residentkva\"}";
        int userId= new Random().nextInt(0,101);

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }
    @Test
    @Description("Обновление только поля Name")
    void successfulPatchOnlyNameFieldTest() {
        String requestData = "{\"name\": \"morpheuskva\"}";
        int userId= new Random().nextInt(0,101);

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }

    @Test
    @Description("Обновление случайных полей")
    void successfulPatchCustomFieldsTest() {
        String requestData = "{\"id3\": \"kvakva3\", \"id4\": \"kvakva4\", \"id5\": \"kvakva5\"}";
        int userId= new Random().nextInt(0,101);

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/user/"+userId)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(userPatchSchema));
    }
}
