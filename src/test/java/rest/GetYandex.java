package rest;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetYandex {

    @Test
    public void getUsers() {
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users/2")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
    }

}
