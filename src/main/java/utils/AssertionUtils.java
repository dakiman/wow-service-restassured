package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionUtils {
    public static void assertErrorMessage(Response res, String jPath, String message) {
        String errorMessage = res.jsonPath().get(jPath);
        Assert.assertEquals(res.statusCode(), 422);
        Assert.assertEquals(errorMessage, message);
    }
}
