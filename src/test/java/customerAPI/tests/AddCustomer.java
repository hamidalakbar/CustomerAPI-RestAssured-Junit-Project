package customerAPI.tests;

import customerAPI.pojo.Customer;
import customerAPI.utilities.CustomerTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddCustomer extends CustomerTestBase {

    @DisplayName("POST new customer: api/customers")
    @Test
    public void addCustomer() {

        // Creating customer by using POJO
        Customer customer = new Customer();
        customer.setName("Mike");
        customer.setAge("25/6");

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .accept(ContentType.JSON) // And accept type header is application/json
                .body(customer) // And customer info included in request body/payload
                .when()
                .post("/api/customers") // When POST request is sent to add a customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_CREATED) // Then response status code should be 201
                .contentType(ContentType.JSON) // And response content type should be application/json
                .body("id", everyItem(notNullValue())) // And response body should contain ID, Name, Age (Years and Months) of all customers
                .body("name", everyItem(notNullValue()))
                .body("age", everyItem(notNullValue()));
    }

    @DisplayName("POST new customer without name: api/customers")
    @Test
    public void addCustomerWithoutName() {

        // Creating customer data by using Map
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("age", "25/6");

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .accept(ContentType.JSON) // And accept type header is application/json
                .body(customerData) // And only customer age is included in request body/payload
                .post("/api/customers") // When POST request is sent to add a customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_BAD_REQUEST) // Then response status code should be 400
                .body("message", equalTo("Bad Request")); // And response body should contain "Bad Request" message
    }

    @DisplayName("POST new customer without age: api/customers")
    @Test
    public void addCustomerWithoutAge() {

        // Creating customer data by using Map
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("name", "Mike");

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .contentType(ContentType.JSON) // And content type header is application/json
                .accept(ContentType.JSON) // And accept type header is application/json
                .body(customerData) // And only customer name is included in request body/payload
                .when()
                .post("/api/customers") // When POST request is sent to add a customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_BAD_REQUEST) // Then response status code should be 400
                .body("message", equalTo("Bad Request")); // And response body should contain "Bad Request" message
    }

    @DisplayName("POST new customer without authorization: api/customers")
    @Test
    public void addCustomerWithoutAuth() {

        // Creating customer by using POJO
        Customer customer = new Customer();
        customer.setName("Mike");
        customer.setAge("25/6");

        given().log().uri()
                .contentType(ContentType.JSON) // Given content type header is application/json
                .accept(ContentType.JSON) // And accept type header is application/json
                .body(customer) // And customer info included in request body/payload
                .when()
                .post("/api/customers") // When POST request is sent to add a customer
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_UNAUTHORIZED) // Then response status code should be 401
                .contentType(ContentType.JSON) // And response content type should be application/json
                .body("message", equalTo("Unauthorized"));
    }
}
