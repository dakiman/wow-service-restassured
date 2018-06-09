import io.restassured.response.Response;
import models.HeroCharacter;
import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import services.HeroCharacterService;
import services.UserService;
import utils.AssertionUtils;
import utils.RandomUtils;
import utils.RequestUtils;
import utils.ValidationTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterTest {
    private static RequestUtils reqUtils;
    private static UserService userService;
    private static HeroCharacterService charService;
    private static ValidationTestUtils validate;

    @BeforeSuite
    public static void setup() {
        ConfigBuilder.defaultSetup();
        userService = new UserService();
        charService = new HeroCharacterService();
        reqUtils = new RequestUtils("character");
        validate  = new ValidationTestUtils("character");
    }

    @Test
    void userCanAddCharacter() {
        HeroCharacter heroCharacter = charService.generateCharacterData();
        User user = userService.generateRegisteredUserWithToken();
        Response res = reqUtils.authPost(user, heroCharacter);
        HeroCharacter resCharacter = res.jsonPath().getObject("", HeroCharacter.class);
        Assert.assertEquals(res.statusCode(), 201);
        Assert.assertEquals(heroCharacter, resCharacter);
    }

    @Test
    void userCantAddInvalidCharacter() {
        User user = userService.generateRegisteredUserWithToken();
        HeroCharacter heroCharacter = charService.generateCharacterData();
        heroCharacter.name = RandomUtils.randomString(10) + "123";
        Response res = reqUtils.authPost(user, heroCharacter);
        AssertionUtils.assertValidationErrorMessage(res, "character", "Error processing character.");
    }

    @Test
    void characterNameRequired() {
        validate.fieldIsRequired("name", charService.generateCharacterData(), userService.generateRegisteredUserWithToken());
    }

    @Test
    void characterRealmRequired() {
        validate.fieldIsRequired("realm", charService.generateCharacterData(), userService.generateRegisteredUserWithToken());
    }

    @Test
    void userCanGetMultipleCharacters() {
        User user = userService.generateRegisteredUserWithToken();
        List<HeroCharacter> characters = new ArrayList<HeroCharacter>();
        int numOfCharacters = RandomUtils.randomInt(1, 10);
        for(int i=0; i<numOfCharacters; i++) {
            characters.add(charService.createCharacterForUser(user));
        }
        Response res = reqUtils.authGet(user);
        List<HeroCharacter> resCharacters = Arrays.asList(res.getBody().as(HeroCharacter[].class));
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(characters, resCharacters);
    }

    @Test
    void userCanDeleteCharacter() {
        User user = userService.generateRegisteredUserWithToken();
        HeroCharacter heroCharacter = charService.createCharacterForUser(user);
        Response res = reqUtils.authDelete("character/" + heroCharacter.id, user);
        AssertionUtils.assertMessage(res, "success", "Character sucessfully deleted.", 200);
    }

    @Test
    void userCanOnlyDeleteOwnedCharacters() {
        User owner = userService.generateRegisteredUserWithToken();
        HeroCharacter heroCharacter = charService.createCharacterForUser(owner);
        User user = userService.generateRegisteredUserWithToken();
        Response res = reqUtils.authDelete("character/" + heroCharacter.id, user);
        AssertionUtils.assertMessage(res, "errors.character[0]", "You do not own this character.", 403);
    }
}

