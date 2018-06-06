package utils;

import io.restassured.response.Response;
import models.User;

import java.lang.reflect.Field;
import java.util.Arrays;

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


    /*
      OVERLOADED METHODS OVERRIDING OBJECTS DEFAULTS
     */
    public void fieldIsRequired(String fieldName, Object data, String route) {
        changeFieldValue(fieldName, data, null);
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " field is required.");
    }

    public void fieldMaximumLength(String fieldName, int max, Object data, String route) {
        changeFieldValue(fieldName, data, RandomUtils.randomString(max + 1));
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " may not be greater than " + max + " characters.");
    }

    public void fieldMinimumLength(String fieldName, int min, Object data, String route) {
        changeFieldValue(fieldName, data, RandomUtils.randomString(min - 1));
        Response res = reqUtils.post(route, data);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " must be at least " + min + " characters.");
    }

    public void fieldIsRequired(String fieldName, Object data) {
        fieldIsRequired(fieldName, data, this.route);
    }

    public void fieldMaximumLength(String fieldName, int max, Object data) {
        fieldMaximumLength(fieldName, max, data, this.route);
    }

    public void fieldMinimumLength(String fieldName, int min, Object data) {
        fieldMinimumLength(fieldName, min, data, this.route);
    }

    /* METHODS INCORPORATING AUTH */

    public void fieldIsRequired(String fieldName, Object data, String route, User user) {
        changeFieldValue(fieldName, data, null);
        Response res = reqUtils.authPost(route, data, user);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " field is required.");
    }

    public void fieldMaximumLength(String fieldName, int max, Object data, String route, User user) {
        changeFieldValue(fieldName, data, RandomUtils.randomString(max + 1));
        Response res = reqUtils.authPost(route, data, user);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " may not be greater than " + max + " characters.");
    }

    public void fieldMinimumLength(String fieldName, int min, Object data, String route, User user) {
        changeFieldValue(fieldName, data, RandomUtils.randomString(min - 1));
        Response res = reqUtils.authPost(route, data, user);
        AssertionUtils.assertValidationErrorMessage(res, "errors." + fieldName + "[0]", "The " + fieldName + " must be at least " + min + " characters.");
    }

    public void fieldIsRequired(String fieldName, Object data, User user) {
        fieldIsRequired(fieldName, data, route, user);
    }

    public void fieldMaximumLength(String fieldName, int max, Object data, User user) {
        fieldMaximumLength(fieldName, max, data, route, user);
    }

    public void fieldMinimumLength(String fieldName, int min, Object data, User user) {
        fieldMinimumLength(fieldName, min, data, route, user);
    }

}
