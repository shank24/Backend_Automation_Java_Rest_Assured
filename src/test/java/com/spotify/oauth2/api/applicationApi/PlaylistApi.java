package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistApi {

    static String userId = "31uxnqcuy7hrnntpso5yaoe56uzy";
    static String access_Token = "BQCPJRbaksYUgOcVL4TcQdO3Uw5gukdaG_W30SujnnahjLGMf9rwm9P36i8NZbURxufHSM0kMUZYr5c7Zi3-MxB8Owzy1BrZDLKNsZCPjywmAzAcl9_rjRWoFWQ1MzTfAt2huInXsRuDHU4d5sY4iw-KPWnmv6GkmSyY2_bAtcomIfWzvR0FqO8wwZUSRXyyW5QBw58OhemmKZZIKdQ1OSqLkYKanvlPohNRwaXmYFyL5GGq";

    public static Response post(Playlist requestPlaylist) {

        return given(getRequestSpec())
                .body(requestPlaylist)
                .header("Authorization","Bearer " + access_Token)
                .when()
                .post("/users/" + userId + "/playlists")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .extract()
                .response();
    }

    public static Response post(Playlist requestPlaylist, String token) {

        return given(getRequestSpec())
                .body(requestPlaylist)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/users/" + userId + "/playlists")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .extract()
                .response();
    }

    public static Response get(String playlistId){

        return given(getRequestSpec())
                .header("Authorization","Bearer " + access_Token)
                .when()
                .get("/playlists/"+playlistId)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response put(Playlist requestPlaylist, String playlistId)
    {

        return given(getRequestSpec())
                .body(requestPlaylist)
                .header("Authorization","Bearer " + access_Token)
                .when()
                .put("/playlists/" + playlistId)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
