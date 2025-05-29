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

public class LoginCourierTests {
    protected final CourierGenerator courierGenerator = new CourierGenerator();
    private CourierAssert courierAssert;
    private int courierID;
    private CourierCreds courierCreds;
    private CourierSteps courierSteps;
    private Courier courier;

    @Before
    @Step("Создание тестовых данных для логина курьера")
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = courierGenerator.randomCourier();
        courierSteps.create(courier);
        courierCreds = CourierCreds.credsFromCourier(courier);
        courierAssert = new CourierAssert();
    }

    @Test
    @DisplayName("Логин курьера успешен")
    public void courierLoginOkValidData() {
        ValidatableResponse responseLoginCourier = courierSteps.login(courierCreds);
        courierAssert.loginCourierOk(responseLoginCourier);
        courierID = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    public void courierLoginErrorEmptyLogin() {
        CourierCreds courierCredsWithoutLogin = new CourierCreds("", courier.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierSteps.login(courierCredsWithoutLogin);
        courierAssert.loginCourierError(responseLoginErrorMessage);

    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    public void courierLoginErrorEmptyPassword() {
        CourierCreds courierCredsWithoutPass = new CourierCreds(courier.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.login(courierCredsWithoutPass);
        courierAssert.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера с пустым полями логина и пароля")
    public void courierLoginErrorEmptyLoginAndPassword() {
        CourierCreds courierCredsWithoutLoginAndPassword = new CourierCreds("", "");
        ValidatableResponse responseLoginErrorMessage = courierSteps.login(courierCredsWithoutLoginAndPassword);
        courierAssert.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера c невалидным логином")
    public void courierLoginErrorAccountNotFound() {
        CourierCreds courierCredsErrorAccountNotFound = new CourierCreds(CourierGenerator.randomCourier().getLogin(), courier.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierSteps.login(courierCredsErrorAccountNotFound);
        courierAssert.loginCourierErrorAccountNotFound(responseLoginErrorMessage);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierID != 0) {
            courierSteps.delete(courierID);
        }
    }
}
