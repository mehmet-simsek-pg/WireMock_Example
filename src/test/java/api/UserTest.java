package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;

public class UserTest extends BaseTest{

    @Test
    public void getUser() {
        when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("name",equalTo( "Mehmet Simsek"));
    }

    @Test
    public void createUser() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Mehmet Simsek");
        body.put("email", "mehmet.simsek@example.com");
        body.put("role", "tester");

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Mehmet Simsek"))
                .body("email", equalTo("mehmet.simsek@example.com"))
                .body("$", hasKey("id"));
    }

    @Test
    public void updateUser() {
        Map<String, Object> body = new HashMap<>();
        body.put("id", 2);
        body.put("name", "Mehmet Simsek");
        body.put("email", "mehmet.simsek@updated.com");
        body.put("role", "qa-lead");

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("message", anyOf(containsString("updated"), containsString("Updated")))
                .body("timestamp", not(emptyOrNullString()));
    }

    @Test
    public void deleteUser() {
        when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }
}
