package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustom;

public class RequestSpecs {

    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .addFilter(withCustom())
                .addHeader("x-api-key", "reqres_e4df7de57a7a4594a8450938db327721")
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
