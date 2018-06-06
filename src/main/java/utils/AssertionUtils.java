package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionUtils {
    public static void assertValidationErrorMessage(Response res, String jPath, String message) {
        assertMessage(res, jPath, message, 422);
    }

    public static void assertMessage(Response res, String jPath, String message, int statusCode) {
        String errorMessage = res.jsonPath().get(jPath);
        Assert.assertEquals(res.statusCode(), statusCode);
        Assert.assertEquals(errorMessage, message);
    }
}
