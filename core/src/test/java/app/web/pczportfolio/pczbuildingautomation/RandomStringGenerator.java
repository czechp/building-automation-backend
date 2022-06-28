package app.web.pczportfolio.pczbuildingautomation;

import java.util.UUID;

public class RandomStringGenerator {
    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomEmail() {
        return UUID.randomUUID().toString() + "@gmail.com";
    }

    public static String randomEmail(String domain) {
        return UUID.randomUUID().toString() + domain;
    }
}
