import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
public class CreateTests {
    private final String userCreateSchema = "createUserResponse-schema.json";
    @Test
    @Description("Успешное создание пользователя со всеми полями")
    void successfulCreateTest(){
        String requestData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/user")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(userCreateSchema))
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
    @Test
    @Description("Успешное создание пользователя с пустыми полями")
    void successfulEmptyCreateTest() {
        String requestData = "{}";

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/user")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(userCreateSchema));
    }

    @Test
    @Description("Успешное создание пользователя со случайными полями")
    void successfulCreateCustomParametersTest() {
        String requestData = "{\"id\": \"kvakva\", \"id2\": \"kvakva2\", \"id3\": \"kvakva3\", \"id4\": \"kvakva4\", \"id5\": \"kvakva5\"}";

        given()
                .body(requestData)
                .header("x-api-key","reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/user")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(userCreateSchema));
    }
}