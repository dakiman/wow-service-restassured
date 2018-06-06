package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionUtils {

    public static void assertMessage(Response res, String messageIndex, String message, int statusCode) {
        String errorMessage = res.jsonPath().get(messageIndex);
        Assert.assertEquals(res.statusCode(), statusCode);
        Assert.assertEquals(errorMessage, message);
    }

    public static void assertValidationErrorMessage(Response res, String messageIndex, String message) {
        assertMessage(res, messageIndex, message, 422);
    }
}
