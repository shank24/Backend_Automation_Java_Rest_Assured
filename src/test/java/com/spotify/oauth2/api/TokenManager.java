package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {

    public static String renewToken(){

        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("refresh_token","AQAqEshO1DXJXKUuHpvO_mC4xj6-hdt9giUhNoEixy5Npp3D3EfaDgxLiAgJ6zAVjnCJZy0GBmnRVtGXoopko0nfbeSzw7pCtx54aeUKF3ZsZuv4XbwDNs37XHtKrDaUg6Y");
        formParams.put("grant_type","refresh_token");
        formParams.put("client_id","d20b8dc9abd34ee38d3eaee105b7c88c");
        formParams.put("client_secret","f8a7d0b2f06e45a5affe6d96ac41feb3");

        Response response = given()
                .baseUri("https://accounts.spotify.com")
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .when()
                .post("/api/token")
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();

        if(response.statusCode()!=200) {
            throw new RuntimeException("ABORT !!!! Renew Token Failed");
        }
        return response.path("access_token");

    }
}
