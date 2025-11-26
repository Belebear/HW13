import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetUsersTest extends BaseTest{

    @Test
    void getUsersSuccessTest() {
        given()
                .log().all()
                .queryParam("page", 2)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("api/users")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void getSingleUserSuccessTest() {
        given()
                .log().all()
                .pathParam("id", 2)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("api/users/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void getSingleUserBadRequestTest() {
        given()
                .log().all()
                .pathParam("id", 123)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("api/users/{id}")
                .then()
                .log().all()
                .statusCode(404);
    }

    
}
