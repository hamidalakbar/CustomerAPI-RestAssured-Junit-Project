package customerAPI.tests;

import customerAPI.utilities.CustomerTestBase;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DeleteCustomer extends CustomerTestBase {

    @DisplayName("DELETE customer: api/customers/{id}")
    @Test
    public void deleteCustomer() {

        given().log().uri()
                .header("Authorization", "toke") // Given authorization token is included in request header
                .pathParam("id", 5) // And customer id provided as path param
                .when()
                .delete("/api/customers/{id}") // When DELETE request is sent to remove customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_NO_CONTENT); // Then response status code should be 204
    }

    @DisplayName("DELETE customer without authorization: api/customers/{id}")
    @Test
    public void deleteCustomerWithoutAuth() {

        given().log().uri()
                .pathParam("id", 10) // Given customer id provided as path param
                .when()
                .delete("/api/customers/{id}") // When DELETE request is sent to remove customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_UNAUTHORIZED) // Then response status code should be 401
                .body("message", equalTo("Unauthorized")); // And response body should contain "Unauthorized" message
    }

    @DisplayName("DELETE customer without id: api/customers")
    @Test
    public void deleteCustomerWithoutId() {

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .when()
                .delete("/api/customers") // When DELETE request is sent to remove customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_NOT_FOUND) // Then response status code should be 404
                .body("message", equalTo("Not Found"));
    }
}
