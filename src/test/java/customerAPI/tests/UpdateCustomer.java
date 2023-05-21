package customerAPI.tests;

import customerAPI.pojo.Customer;
import customerAPI.utilities.CustomerTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateCustomer extends CustomerTestBase {

    @DisplayName("PUT update customer name: api/customers/{id}")
    @Test
    public void updateCustomerName() {

        // Creating customer by using POJO
        Customer customer = new Customer();
        customer.setName("Steve");
        customer.setAge("25/6");

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .pathParam("id", 15) // And customer id provided as path param
                .body(customer) // And customer info with updated name included in request body/payload
                .when()
                .put("/api/customers/{id}") // When PUT request is sent to change/update name of customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_NO_CONTENT); // Then response status code should be 204
    }

    @DisplayName("PUT update customer age: api/customers/{id}")
    @Test
    public void updateCustomerAge() {

        // Creating customer by using POJO
        Customer customer = new Customer();
        customer.setName("Steve");
        customer.setAge("28/6");

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .pathParam("id", 15) // And customer id provided as path param
                .body(customer) // And customer info with updated age included in request body/payload
                .when()
                .put("/api/customers/{id}") // When PUT request is sent to change/update age of customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_NO_CONTENT); // Then response status code should be 204
    }

    @DisplayName("PUT update customer info without authorization: apicustomers/{id}")
    @Test
    public void updateCustomerInfoWithoutAuth() {

        // Creating customer by using POJO
        Customer customer = new Customer();
        customer.setName("Julia");
        customer.setAge("22/8");

        given().log().uri()
                .contentType(ContentType.JSON) // Given content type header is application/json
                .pathParam("id", 25) // And customer id provided as path param
                .body(customer) // And updated customer info included in request body/payload
                .when()
                .put("/api/customers/{id}") // When PUT request is sent to change/update name of customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_UNAUTHORIZED) // Then response status code should be 401
                .body("message", equalTo("Unauthorized")); // And response body should contain "Unauthorized" message
    }

    @DisplayName("PUT update customer info without providing data: api/customers/{id}")
    @Test
    public void updateCustomerInfoWithoutData() {

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .pathParam("id", 25) // And customer id provided as path param
                .when()
                .put("/api/customers/{id}") // When PUT request is sent to change/update with empty request body/payload
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_BAD_REQUEST) // Then response status code should be 400
                .body("message", equalTo("Bad Request")); // And response body should contain "Bad Request message"
    }
}
