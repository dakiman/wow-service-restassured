package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RequestUtils {
    private String route;

    public RequestUtils() {
    }

    public RequestUtils(String route) {
        this.route = route;
    }


    public Response get() {
        return when().get(this.route);
    }

    public Response get(String route) {
        return when().get(route);
    }

    public Response authGet(User user) {
        return given().auth().oauth2(user.token).get(this.route);
    }

    public Response authGet(String route, User user) {
        return given().auth().oauth2(user.token).get(route);
    }

    public Response post(String route, Object data) {
        return given().
                    body(data).
                when().
                    post(route);
    }

    public Response post(Object data) {
        return given().
                    body(data).
                when().
                    post(this.route);
    }

    public Response authPost(String route, User user,  Object data){
        return given().
                    body(data).
                    auth().oauth2(user.token).
                when().
                    post(route);
    }

    public Response authPost(User user, Object data){
        return given().
                    body(data).
                    auth().oauth2(user.token).
                when().
                    post(route);
    }

    public Response authDelete(User user){
        return given().
                auth().oauth2(user.token).
                when().
                delete(route);
    }

    public Response authDelete(String route, User user){
        return given().
                auth().oauth2(user.token).
                when().
                delete(route);
    }
}
