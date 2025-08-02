package apiTests.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class LoginSpecification {
    public static RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres-free-v1")
            .contentType(JSON)

            .log().uri()
            .log().body()
            .log().headers()
            .basePath("/api/login");

    public static RequestSpecification loginRequestNoContentSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres-free-v1")

            .log().uri()
            .log().body()
            .log().headers()
            .basePath("/api/login");

    public static ResponseSpecification loginResponse200Spec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification loginResponse415UnsupportedMTSpec = new ResponseSpecBuilder()
            .expectStatusCode(415)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification loginResponse400BadRequestSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();

}
