package rest;

import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetYandex {

    static Logger log = LogManager.getLogger(GetYandex.class);

    @Test
    public void getUsers() {
        log.info("Start getUsers test EVG");
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users/2")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
    }

}
