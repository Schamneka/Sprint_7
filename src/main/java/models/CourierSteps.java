package models;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static constant.ApiEndpoint.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    public CourierSteps() {
        RestAssured.baseURI = BASE_URL;
    }

    public ValidatableResponse create(Courier courier) {
        return (ValidatableResponse) given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_POST_CREATE);
    }

    public ValidatableResponse login(CourierCreds creds) {
        return (ValidatableResponse) given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(COURIER_POST_LOGIN);
    }

    public ValidatableResponse delete(int id) {
        return (ValidatableResponse) given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_DELETE + "/" + id);
    }
}