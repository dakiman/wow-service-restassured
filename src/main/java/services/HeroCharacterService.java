package services;

import io.restassured.response.Response;
import models.HeroCharacter;
import models.User;
import utils.RandomUtils;
import utils.RequestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HeroCharacterService {
    private RequestUtils reqUtils;
    private static UserService userService;
    private String [] names = {"Sernaos", "Dakis", "Dakimans", "Cirna", "Trolleyvolle", "Packaged"};


    public HeroCharacter generateCharacterData() {
        HeroCharacter heroChar = new HeroCharacter();
        heroChar.name = names[RandomUtils.randomInt(0, names.length - 1)];
        heroChar.realm = "The Maelstrom";
        return heroChar;
    }

    public HeroCharacterService() {
        this.reqUtils = new RequestUtils("character");
        userService = new UserService();
    }

    public HeroCharacter createCharacterForUser(User user) {
        HeroCharacter heroChar = generateCharacterData();
        Response res = reqUtils.authPost(user, heroChar);
        HeroCharacter resChar = res.as(HeroCharacter.class);
        heroChar = resChar;
        return heroChar;
    }


}
