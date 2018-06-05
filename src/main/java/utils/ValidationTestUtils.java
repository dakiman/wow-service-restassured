package utils;

import io.restassured.response.Response;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ValidationTestUtils {
    private String route;
    private RequestUtils reqUtils;

    public ValidationTestUtils() {
        reqUtils = new RequestUtils();
    }

    public ValidationTestUtils(String route) {
        reqUtils = new RequestUtils();
        this.route = route;
    }

    private void changeFieldValue(String fieldName, Object obj, String value) {
        try {
            Class aClass = obj.getClass();
            Field field = aClass.getField(fieldName);
            field.set(obj, value);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void fieldIsRequired(String fieldName, Object data) {
        this.changeFieldValue(fieldName, data, null);
        Response res = reqUtils.post(this.route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " field is required.");
    }

    public void fieldMaximumLength(String fieldName, int max, Object data) {
        this.changeFieldValue(fieldName, data, RandomUtils.randomString(max + 1));
        Response res = reqUtils.post(this.route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " may not be greater than " + max + " characters.");
    }

    public void fieldMinimumLength(String fieldName, int min, Object data) {
        this.changeFieldValue(fieldName, data, RandomUtils.randomString(min - 1));
        Response res = reqUtils.post(this.route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " must be at least " + min + " characters.");
    }

    /*
      OVERLOADED METHODS OVERRIDING OBJECTS DEFAULTS
     */
    public void fieldIsRequired(String fieldName, Object data, String route) {
        this.changeFieldValue(fieldName, data, null);
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " field is required.");
    }

    public void fieldMaximumLength(String fieldName, int max, Object data, String route) {
        this.changeFieldValue(fieldName, data, RandomUtils.randomString(max + 1));
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " may not be greater than " + max + " characters.");
    }

    public void fieldMinimumLength(String fieldName, int min, Object data, String route) {
        this.changeFieldValue(fieldName, data, RandomUtils.randomString(min - 1));
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " must be at least " + min + " characters.");
    }

}
