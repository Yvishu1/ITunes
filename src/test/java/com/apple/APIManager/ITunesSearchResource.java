package com.apple.APIManager;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ITunesSearchResource {

    private static final String BASE_URI = "https://itunes.apple.com";

    private static RequestSpecification buildRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType("application/json")
                .setBaseUri(BASE_URI)
                .setBasePath("/search")
                .build();
    }

    private static ResponseSpecification buildResponseSpec() {
        return new ResponseSpecBuilder()

                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    private static ResponseSpecification buildResponseSpecIgnoreStatusCode() {
        return new ResponseSpecBuilder()


                .expectContentType(ContentType.JSON)
                .build();
    }

    public static Response search(Map<String, String> queryParams) {



        RequestSpecification requestSpec = buildRequestSpec();
        ResponseSpecification responseSpec = buildResponseSpec();

        Response response=given()

                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get();
         response.then()

                .spec(responseSpec);


        return response;
    }

    public static Response searchAndIgnoreStatusCode(Map<String, String> queryParams) {



        RequestSpecification requestSpec = buildRequestSpec();
        ResponseSpecification responseSpec = buildResponseSpecIgnoreStatusCode();

        Response response=given()

                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get();
        response.then()

                .spec(responseSpec);


        return response;
    }

}
