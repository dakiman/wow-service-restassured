import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.*;

public class ConfigBuilder {
    public static void defaultSetup() {
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.requestSpecification = new RequestSpecBuilder().
                addFilter(new RequestLoggingFilter()).
                addFilter(new ResponseLoggingFilter()).
                addFilter(new ErrorLoggingFilter()).
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json").
                build();
    }
}
