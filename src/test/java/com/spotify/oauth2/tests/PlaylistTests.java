package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.util.FakerUtils.generateDesc;
import static com.spotify.oauth2.util.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth 2.0")
@Feature("Playist Api")

public class PlaylistTests {

    /**
     * Post A Playlist
     */

    @Story("Create A Playlist Story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("12456")
    @Description("Test - Create A Playlist")
    @Test(description = "Should Be Able To Create A Playlist")
    public void shouldBeAbleToCreatePlaylist()
    {
        Playlist requestPlaylist = getPlaylist(generateName(), generateDesc());
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201.getCode());
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
    }

    /**
     * Get A Playlist
     */

    @Story("Get A Playlist Story")
    @Description("Test - Get A Playlist")
    @Test(description = "Should Be Able To Get A Playlist")
    public void shouldBeAbleToFetchPlaylist(){

        Playlist requestPlaylist = getPlaylist("Updated New Playlist", "Updated  Reggae");
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
    }

    /**
     * Update A Playlist
     */

    @Story("Update A Playlist Story")
    @Description("Test - Update A Playlist")
    @Test(description = "Should Be Able To Update A Playlist")
    public void shouldBeAbleToUpdatePlaylist(){

        Playlist requestPlaylist = getPlaylist(generateName(), generateDesc());
        Response response = PlaylistApi.put(requestPlaylist,DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());
    }


    /**
     * Negative Scenario
     * Post A Playlist
     */

    @Story("Create A Playlist Story Without Name")
    @Description("Test - Create A Playlist Without Name")
    @Test(description = "Should Be Not Able To Create A Playlist Without Name")
    public void shouldNotBeAbleToCreatePlaylistWithoutName()
    {
        Playlist requestPlaylist = getPlaylist("", generateDesc());
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400.getCode());

        Error errorResponse = response.as(Error.class);
        assertErrorCode(errorResponse, StatusCode.CODE_400.getCode(), StatusCode.CODE_400.getMsg());
    }


    /**
     * Negative Scenario
     * Post A Playlist
     */
    @Story("Create A Playlist Story Without Token")
    @Description("Test - Create A Playlist Without Token")
    @Test(description = "Should Be Not Able To Create A Playlist Without Token")
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken()
    {
        String invalidToken = "12345";
        Playlist requestPlaylist = getPlaylist("", generateDesc());
        Response response = PlaylistApi.post(requestPlaylist,invalidToken);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401.getCode());

        Error errorResponse = response.as(Error.class);
        assertErrorCode(errorResponse, StatusCode.CODE_401.getCode(), StatusCode.CODE_401.getMsg());

    }

    //Utils method
    @Step
    private Playlist getPlaylist(String s, String blues) {
        return Playlist.builder()
                .name(s)
                .description(blues)
                ._public(false)
                .build();

    }

    @Step
    private void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    private void assertErrorCode(Error errorResponse, int i, String s) {
        assertStatusCode(errorResponse.getError().getStatus(), i);
        assertThat(errorResponse.getError().getMessage(), equalTo(s));
    }


    @Step
    private void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

}
