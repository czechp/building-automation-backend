package app.web.pczportfolio.pczbuildingautomation;

import java.util.UUID;

public class RandomStringGenerator {
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static String getRandomEmail() {
        return UUID.randomUUID().toString() + "@gmail.com";
    }

    public static String getRandomEmail(String domain) {
        return UUID.randomUUID().toString() + domain;
    }
}
