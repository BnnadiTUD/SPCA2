package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import model.Customer;

@QuarkusTest
class CustomerAuthResourceTest {

    @Test
    void customerRegistrationStoresPasswordAndLoginSucceeds() {
        String email = "security.customer@example.com";
        String password = "SecurePass123";

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Security Customer",
                          "email": "%s",
                          "password": "%s",
                          "address": "1 Secure Street",
                          "preferredPaymentMethod": "CARD"
                        }
                        """.formatted(email, password))
                .when()
                .post("/customers/register")
                .then()
                .statusCode(200)
                .body("email", equalTo(email))
                .body("id", notNullValue());

        Customer persisted = Customer.find("email", email).firstResult();
        org.junit.jupiter.api.Assertions.assertNotNull(persisted);
        org.junit.jupiter.api.Assertions.assertEquals(password, persisted.password);

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "email": "%s",
                          "password": "%s"
                        }
                        """.formatted(email, password))
                .when()
                .post("/customers/login")
                .then()
                .statusCode(200)
                .body("email", equalTo(email))
                .body("message", equalTo("Customer login successful"));
    }

    @Test
    void customerRegistrationRejectsWeakPassword() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Weak Password User",
                          "email": "weak.password@example.com",
                          "password": "short",
                          "address": "2 Validation Road",
                          "preferredPaymentMethod": "PAYPAL"
                        }
                        """)
                .when()
                .post("/customers/register")
                .then()
                .statusCode(400);
    }
}
