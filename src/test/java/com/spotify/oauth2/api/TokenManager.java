package com.spotify.oauth2.api;

import com.spotify.oauth2.util.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

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
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("grant_type",ConfigLoader.getInstance().getGrantType());
        formParams.put("client_id",ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());

        Response response = RestResource.postAccount(formParams);

        if(response.statusCode()!=200) {
            throw new RuntimeException("ABORT !!!! Renew Token Failed");
        }
        return response;

    }
}
