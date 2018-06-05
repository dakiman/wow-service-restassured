package services;

import models.User;
import utils.RandomUtils;
import utils.RequestUtils;

public class UserService {
    private RequestUtils reqUtils;

    public UserService() {
        this.reqUtils = new RequestUtils();
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
        reqUtils.post("register", user);
        return user;
    }

}
