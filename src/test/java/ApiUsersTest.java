import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ApiUsersTest extends BaseTest {

    private final RequestSpecification spec = new RequestSpecBuilder()
            .addHeader("x-api-key", "reqres-free-v1")
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .build();

    @Test
    void getUsersSuccessTest() {
        given()
                .spec(spec)
                .queryParam("page", 2)
                .when()
                .get("api/users")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void getSingleUserSuccessTest() {
        given()
                .spec(spec)
                .pathParam("id", 2)
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
                .spec(spec)
                .pathParam("id", 123)
                .when()
                .get("api/users/{id}")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    void createUserSuccessTest() {
        given()
                .spec(spec)
                .body("""
                        {
                            "name": "Max",
                            "job": "QA"
                        }
                        """)
                .when()
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Max"))
                .body("job", is("QA"));
    }

    @Test
    void createUserUnsuccessTest() {
        given()
                .spec(spec)
                .body("")
                .when()
                .post("api/user")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", is("Request body cannot be empty for JSON endpoints"));
    }
}
