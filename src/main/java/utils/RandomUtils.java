package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/*
  SIMPLE CLASS USED TO ABSTRACT SOME ANNOYING SYNTAX
 */
public class RandomUtils {

    public static String randomString() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String randomEmail() {
        return RandomStringUtils.randomAlphabetic(8) + "@" + RandomStringUtils.randomAlphabetic(6) + ".com";
    }

    public static String randomString(int size) {
        return RandomStringUtils.randomAlphabetic(size);
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
