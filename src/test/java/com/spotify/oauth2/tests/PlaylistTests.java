package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(),equalTo(201));


        //Deserialzation
        Playlist responsePlaylist = response.as(Playlist.class);
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

        Response response = PlaylistApi.get("7bCg15yQ33NyMiTR7Hah3N");
        assertThat(response.statusCode(),equalTo(200));

        Playlist responsePlaylist = response.as(Playlist.class);
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

        Response response = PlaylistApi.put(requestPlaylist,"7bCg15yQ33NyMiTR7Hah3N");
        assertThat(response.statusCode(),equalTo(200));
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

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(),equalTo(400));

        Error errorResponse = response.as(Error.class);
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
        String invalidToken = "12345";

        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("Updated  Reggae")
                .setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist,invalidToken);
        assertThat(response.statusCode(),equalTo(401));

        Error errorResponse = response.as(Error.class);
        assertThat(errorResponse.getError().getStatus(),equalTo(401));
        assertThat(errorResponse.getError().getMessage(),equalTo("Invalid access token"));

    }


}
