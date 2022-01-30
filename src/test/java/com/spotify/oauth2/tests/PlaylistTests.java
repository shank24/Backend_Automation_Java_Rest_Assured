package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    /**
     * Post A Playlist
     */
    @Test
    public void shouldBeAbleToCreatePlaylist()
    {
        Playlist requestPlaylist = new Playlist()
                .setName("New Playlist")
                .setDescription("Blues")
                .setPublic(false);


            Playlist responsePlaylist = given(getRequestSpec())
                    .body(requestPlaylist)
                    .when()
                    .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                    .then()
                    .spec(getResponseSpec())
                    .assertThat()
                    .statusCode(201)
                    .extract()
                    .response()
                    .as(Playlist.class);

            assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
            assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
            assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));

    }

    /**
     * Get A Playlist
     */
    @Test
    public void shouldBeAbleToFetchPlaylist(){


        Playlist requestPlaylist = new Playlist()
                .setName("Updated New Playlist")
                .setDescription("Updated  Reggae")
                .setPublic(false);

        Playlist responsePlaylist = given(getRequestSpec())
                .when()
                .get("/playlists/7bCg15yQ33NyMiTR7Hah3N")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .as(Playlist.class);

        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));
    }

    /**
     * Update A Playlist
     */
    @Test
    public void shouldBeAbleToUpdatePlaylist(){

        Playlist requestPlaylist = new Playlist()
        .setName("Updated New Playlist")
        .setDescription("Updated  Reggae")
        .setPublic(false);

        given(getRequestSpec())
                .body(requestPlaylist)
                .when()
                .put("/playlists/7bCg15yQ33NyMiTR7Hah3N")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(200);

    }


    /**
     * Negative Scenario
     * Post A Playlist
     */
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName()
    {
        Playlist requestPlaylist = new Playlist()
        .setName("")
        .setDescription("Updated  Reggae")
        .setPublic(false);


        Error errorResponse = given(getRequestSpec())
                .body(requestPlaylist)
                .when()
                .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(400)
                .extract()
                .response()
                .as(Error.class);


        assertThat(errorResponse.getError().getStatus(),equalTo(400));
        assertThat(errorResponse.getError().getMessage(),equalTo("Missing required field: name"));

    }

    /**
     * Negative Scenario
     * Post A Playlist
     */
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken()
    {
        Playlist requestPlaylist = new Playlist()
        .setName("")
        .setDescription("Updated  Reggae")
        .setPublic(false);


        Error errorResponse = given()
                .baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer " + "12345")
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(requestPlaylist)
                .when()
                .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                .then()
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(401)
                .extract()
                .response()
                .as(Error.class);

        assertThat(errorResponse.getError().getStatus(),equalTo(401));
        assertThat(errorResponse.getError().getMessage(),equalTo("Invalid access token"));

    }


}
