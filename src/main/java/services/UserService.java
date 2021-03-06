package services;

import io.restassured.response.Response;
import models.User;
import utils.RandomUtils;
import utils.RequestUtils;

public class UserService {
    private static RequestUtils reqUtils;

    public UserService() {
        reqUtils = new RequestUtils();
    }

    private void generateTokenForUser(User user) {
        if (user.token == null) {
            Response res = reqUtils.post("login", user);
            user.token = res.jsonPath().getString("token");
        }
    }

    private void registerUser(User user) {
        Response res = reqUtils.post("register", user);
        user.id = res.jsonPath().getInt("user.id");
    }

    public User generateDefaultUser() {
        User user = new User();
        user.password = RandomUtils.randomString();
        user.name = RandomUtils.randomString();
        user.email = RandomUtils.randomEmail();
        return user;
    }

    public User generateRegisteredUser() {
        User user = this.generateDefaultUser();
        registerUser(user);
        return user;
    }

    public User generateRegisteredUserWithToken() {
        User user = this.generateDefaultUser();
        registerUser(user);
        generateTokenForUser(user);
        return user;
    }



}
