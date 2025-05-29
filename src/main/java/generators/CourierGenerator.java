package generators;

import models.Courier;

import static utils.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .setLogin(randomString())
                .setPassword(randomString())
                .setFirstName(randomString());
    }

}
