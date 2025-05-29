package CourierTest;

import generators.CourierGenerator;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierAssert;
import models.CourierCreds;
import models.CourierSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTests {
    protected final CourierGenerator courierGenerator = new CourierGenerator();
    int courierId;
    private CourierSteps courierSteps;
    private Courier courier;
    private CourierAssert courierAssert;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = courierGenerator.randomCourier();
        courierAssert = new CourierAssert();
    }

    @Test
    @DisplayName("Создание нового курьера")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierSteps.create(courier);
        CourierCreds courierCreds = CourierCreds.credsFromCourier(courier);
        courierId = courierSteps.login(courierCreds).extract().path("id");
        courierAssert.createCourierOk(responseCreateCourier);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина")
    public void courierCanNotBeCreatedWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierSteps.create(courier);
        courierAssert.createCourierError(responseNullLogin);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем пароля")
    public void courierCanNotBeCreatedWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierSteps.create(courier);
        courierAssert.createCourierError(responseNullPassword);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина и пароля")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierSteps.create(courier);
        courierAssert.createCourierError(responseNullFields);
    }

    @Test
    @DisplayName("Создание курьера с ранее зарегистрированным логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierSteps.create(courier);
        ValidatableResponse responseCreateCourier = courierSteps.create(courier);
        courierAssert.createCourierSameLoginError(responseCreateCourier);
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.delete(courierId);
        }
    }

}