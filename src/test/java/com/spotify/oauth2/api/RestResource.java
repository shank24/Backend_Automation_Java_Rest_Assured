package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getAccountRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response post(Object payload, String token, String path) {

        return given(getRequestSpec())
                .body(payload)
                .header("Authorization","Bearer " + token)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .extract()
                .response();
    }

    public static Response postAccount(HashMap<String,String> formParams){

        return given(getAccountRequestSpec())
                .formParams(formParams)
                .when()
                .post("/api/token")
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response get(String token, String path ){

        return given(getRequestSpec())
                .header("Authorization","Bearer " + token)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response put(Object payload, String token, String path)
    {

        return given(getRequestSpec())
                .body(payload)
                .header("Authorization","Bearer " + token)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
