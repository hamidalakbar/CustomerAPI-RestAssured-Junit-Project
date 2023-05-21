package customerAPI.tests;

import customerAPI.utilities.CustomerTestBase;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ListOfCustomers extends CustomerTestBase {

    @DisplayName("GET all customers: api/customers ")
    @Test
    public void getAllCustomers() {

        given().log().uri()
                .header("Authorization", "token") // And authorization token is included in request header
                .accept(ContentType.JSON) // Given accept type header is application/json
                .when()
                .get("/api/customers") // When GET request is sent to retrieve list of all customers
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK) // Then response status code should be 200
                .contentType(ContentType.JSON) // And response content type should be application/json
                .body("id", everyItem(notNullValue())) // And response body should contain ID, Name, Age (Years and Months) of all customers
                .body("name", everyItem(notNullValue()))
                .body("age", everyItem(notNullValue()));
    }

    @DisplayName("GET empty list of customers: api/customers")
    @Test
    public void getEmptyListOfCustomers() {

        given().log().uri()
                .header("Authorization", "token") // Given authorization token is included in request header
                .accept(ContentType.JSON) // And accept type header is application/json
                .when()
                .get("/api/customers") // When GET request is sent to retrieve list of all customers
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_OK) // Then response status code should be 200
                .contentType(ContentType.JSON) // And response content type should be application/json
                .body("message", equalTo("Customer list is empty"));
    }

    @DisplayName("GET without authorization")
    @Test
    public void getAllCustomersWithoutAuth() {

        given().log().uri()
                .accept(ContentType.JSON) // Given accept type is application/json
                .when()
                .get("/api/customers") // When GET request is sent to retrieve list of all customers
                .then().log().ifValidationFails()
                .statusCode(HttpStatus.SC_UNAUTHORIZED) // Then response status code should be 401
                .body("message", equalTo("Unauthorized"));
    }
}
