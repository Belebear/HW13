package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .addHeader("x-api-key", "reqres_e4df7de57a7a4594a8450938db327721")
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
