package OrderTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.OrderSteps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreateTest {
    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        OrderSteps orderSteps = new OrderSteps();
        ValidatableResponse responseOrderList = orderSteps.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}