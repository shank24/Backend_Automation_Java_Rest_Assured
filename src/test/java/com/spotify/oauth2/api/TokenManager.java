package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String access_Token;
    private static Instant expiry_Time;

    public static String getToken(){

        //If current time is after or greater than the expiry_Time Or
        //If token ==null
        try {

            if(access_Token==null || Instant.now().isAfter(expiry_Time))
            {
                System.out.println("!! Renewing Token ... !!");
                Response response = renewToken();
                access_Token = response.path("access_token");
                int expiry_Duration_In_Seconds = response.path("expires_in");
                //Subtracting 5 minutes, to be on safer side.
                expiry_Time = Instant.now().plusSeconds(expiry_Duration_In_Seconds - 300);
            }
            else {
                System.out.println("Token is good to use");
            }
        }
        catch (Exception e){
            throw new RuntimeException("ABORT !!!! Renew Token Failed");
        }
        return access_Token;
    }


    private static Response renewToken(){

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
                .log()
                .all()
                .post("/api/token")
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();

        if(response.statusCode()!=200) {
            throw new RuntimeException("ABORT !!!! Renew Token Failed");
        }
        return response;

    }
}
