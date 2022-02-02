package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    /**
     * Post A Playlist
     */
    @Test
    public void shouldBeAbleToCreatePlaylist()
    {
        Playlist requestPlaylist = getPlaylist("New Playlist", "Blues");
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
    }

    /**
     * Get A Playlist
     */
    @Test
    public void shouldBeAbleToFetchPlaylist(){

        Playlist requestPlaylist = getPlaylist("Updated New Playlist", "Updated  Reggae");
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), 200);
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
    }

    /**
     * Update A Playlist
     */
    @Test
    public void shouldBeAbleToUpdatePlaylist(){

        Playlist requestPlaylist = getPlaylist("Updated New Playlist", "Updated  Reggae");
        Response response = PlaylistApi.put(requestPlaylist,DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), 200);
    }


    /**
     * Negative Scenario
     * Post A Playlist
     */
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName()
    {
        Playlist requestPlaylist = getPlaylist("", "Updated  Reggae");
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);

        Error errorResponse = response.as(Error.class);
        assertErrorCode(errorResponse, 400, "Missing required field: name");

    }


    /**
     * Negative Scenario
     * Post A Playlist
     */
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken()
    {
        String invalidToken = "12345";
        Playlist requestPlaylist = getPlaylist("", "Updated  Reggae");
        Response response = PlaylistApi.post(requestPlaylist,invalidToken);
        assertStatusCode(response.statusCode(), 401);

        Error errorResponse = response.as(Error.class);
        assertErrorCode(errorResponse, 401, "Invalid access token");

    }

    //Utils method
    private Playlist getPlaylist(String s, String blues) {
        return Playlist.builder()
                .name(s)
                .description(blues)
                ._public(false)
                .build();

    }

    private void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    private void assertErrorCode(Error errorResponse, int i, String s) {
        assertStatusCode(errorResponse.getError().getStatus(), i);
        assertThat(errorResponse.getError().getMessage(), equalTo(s));
    }


    private void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

}
