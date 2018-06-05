import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import models.User;
import services.UserService;
import utils.AssertionUtils;
import utils.RandomUtils;
import utils.RequestUtils;
import utils.ValidationTestUtils;

public class UserTest {
    private static UserService userService;
    private static RequestUtils reqUtils;
    ValidationTestUtils validate = new ValidationTestUtils("register");

    @BeforeSuite
    public static void setup() {
        ConfigBuilder.defaultSetup();
        userService = new UserService();
        reqUtils = new RequestUtils("register");
    }

    @Test
    void userShouldRegister() {
        User user = userService.generateDefaultUser();
        Response res = reqUtils.post(user);
        User registeredUser = res.jsonPath().getObject("user", User.class);
        Assert.assertEquals(res.statusCode(), 201);
        Assert.assertEquals(user, registeredUser);
    }

    @Test
    void loginShouldReturnUser() {
        User user = userService.generateRegisteredUser();
        Response res = reqUtils.post("login", user);
        User responseUser = res.jsonPath().getObject("user", User.class);
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(user, responseUser);
    }

    @Test
    void emailMustBeValid() {
        User user = userService.generateDefaultUser();
        user.email = RandomUtils.randomString();
        Response res = reqUtils.post(user);
        AssertionUtils.assertErrorMessage(res, "errors.email[0]", "The email must be a valid email address.");
    }

    @Test
    void emailMustBeUnique()
    {
        User user = userService.generateRegisteredUser();
        User newUser = userService.generateDefaultUser();
        newUser.email = user.email;
        Response res = reqUtils.post(newUser);
        AssertionUtils.assertErrorMessage(res, "errors.email[0]", "The email has already been taken.");
    }

    @Test
    void userNameIsRequired() {
        validate.fieldIsRequired("name", userService.generateDefaultUser());
    }

    @Test
    void userEmailIsRequired() {
        validate.fieldIsRequired("email", userService.generateDefaultUser(), "register");
    }

    @Test
    void userPasswordIsRequired()
    {
        validate.fieldIsRequired("password", userService.generateDefaultUser());
    }

    @Test
    void nameMaxLength() {
        validate.fieldMaximumLength("name", 30, userService.generateDefaultUser());
    }

    @Test
    void passwordMaxLength() {
        validate.fieldMaximumLength("password", 32, userService.generateDefaultUser(), "register");
    }

    @Test
    void passwordMinLength()
    {
        validate.fieldMinimumLength("password", 8, userService.generateDefaultUser());
    }

    @Test
    void nameMinLength()
    {
        validate.fieldMinimumLength("name", 2, userService.generateDefaultUser(), "register");
    }




}
