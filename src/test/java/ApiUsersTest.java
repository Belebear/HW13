import constants.Endpoints;
import models.ErrorResponseBodyModel;
import models.SingleUserRequestBodyModel;
import models.SingleUserResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpec;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("api_test")
public class ApiUsersTest extends BaseTest {

    @DisplayName("Получение списка пользователей")
    @Test
    void getUsersSuccessTest() {
        step("Отправка запроса на получение списка пользователей", () -> given()
                .spec(RequestSpecs.baseSpec())
                .queryParam("page", 2)
                .when()
                .get(Endpoints.USERS)
                .then()
                .spec(ResponseSpec.baseResponse(200))
                .body("data.id", hasItems(7, 8, 9, 10, 11, 12)));
    }

    @DisplayName("Получение информации о пользователе")
    @Test
    void getSingleUserSuccessTest() {
        step("Отправка запроса на получение информации о пользователе", () -> given()
                .spec(RequestSpecs.baseSpec())
                .pathParam("id", 2)
                .when()
                .get(Endpoints.USERS_ID)
                .then()
                .spec(ResponseSpec.baseResponse(200))
                .body("data.id", is(2)));
    }

    @DisplayName("Получение информации о несуществуюшем пользователе")
    @Test
    void getSingleUserBadRequestTest() {
        step("Отправка GET запроса на получение юзера по id", () -> given()
                .spec(RequestSpecs.baseSpec())
                .pathParam("id", 123)
                .when()
                .get(Endpoints.USERS_ID)
                .then()
                .spec(ResponseSpec.baseResponse(404)));
    }

    @DisplayName("Создание пользователя")
    @Test
    void createUserSuccessTest() {
        SingleUserRequestBodyModel userData = step("Создание тела запроса", () -> SingleUserRequestBodyModel.builder()
                .name("Max")
                .job("QA")
                .build());
        SingleUserResponseBodyModel response = step("Отправка post запроса на создание юзера, валидация ответа", () -> given()
                .spec(RequestSpecs.baseSpec())
                .body(userData)
                .when()
                .post(Endpoints.USERS)
                .then()
                .spec(ResponseSpec.baseResponse(201))
                .extract().body().as(SingleUserResponseBodyModel.class));
        step("Проверка отвера", () -> {
            assertEquals(response.getName(), "Max");
            assertEquals(response.getJob(), "QA");
        });
    }

    @DisplayName("Создание пользователя с пустым телом запроса")
    @Test
    void createUserUnsuccessTest() {
        ErrorResponseBodyModel response = step("Отправка запроса на создание юзера с пустым телом, проверка статуса 400", () -> given()
                .spec(RequestSpecs.baseSpec())
                .body("")
                .when()
                .post(Endpoints.USER)
                .then()
                .spec(ResponseSpec.baseResponse(400))
                .extract().body().as(ErrorResponseBodyModel.class));
        step("Проверка содержания ответа", () -> {
            assertEquals(response.getError(), "Empty request body");
            assertEquals(response.getMessage(), "Request body cannot be empty for JSON endpoints");
        });
    }
}
