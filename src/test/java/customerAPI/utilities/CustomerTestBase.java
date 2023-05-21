package customerAPI.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class CustomerTestBase {

    @BeforeAll
    public static void setUpBaseUri() {
        RestAssured.baseURI = "http://localhost:8000";
    }
}
