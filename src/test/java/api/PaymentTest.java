package api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PaymentTest extends BaseTest{

    @Test
    public void sendPayment() {
        given()
                .contentType("application/json")
                .body("{\"amount\":100}")
                .when()
                .post("/payments")
                .then()
                .statusCode(201)
                .body("status", equalTo("ACCEPTED"))
                .body("echoAmount", equalTo("100"));
    }

    @Test
    public void getPaymentById() {
        when()
                .get("/payments/10")
                .then()
                .statusCode(200)
                .body("id", equalTo(10))
                .body("status", equalTo("ACCEPTED"))
                .body("amount", equalTo(100));
    }

    /**
     * PUT - Ödemeyi güncelleme
     */
    @Test
    public void updatePayment() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"id\":10,\"amount\":150,\"status\":\"UPDATED\"}")
                .when()
                .put("/payments/10")
                .then()
                .statusCode(200)
                .body("message", containsString("updated"))
                .body("updatedAmount", equalTo(150));
    }

    /**
     * DELETE - Ödemeyi silme
     */
    @Test
    public void deletePayment() {
        when()
                .delete("/payments/10")
                .then()
                .statusCode(204);
    }
}
