package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_Token = "BQCJpB1NjoRmIVNDsSGgHj0V6CS7KGa7hEtqr18ca1S3CrSRJztbZu71XNZd670ElyTRB5rDjKBscSWp0WXO9wQsJuD4DnLlBkHKBAl11dcAKO0yVGogNFSbss1yDuQ1Sbxcn9aWJDw4Tqn0zoaEG4JvlJ_ejycxWULVWz0jQ3waJY_L3aBmnsw9hPHypitfTH2AcgDwBv3v6_4OsS5U_MMl6vd_PAdj-6g3Zv5PJ6irJjYV";

    @BeforeClass
    public void setup(){

        /**
         * RequestSpec Builder
         */
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization","Bearer " + access_Token)
        //requestSpecBuilder.addHeader("x-api-key", ObjectReader.reader.getKey());
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        /**
         * ResponseSpec Builder
         */
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                //.expectStatusCode(201)
                //.expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

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


            Playlist responsePlaylist = given(requestSpecification)
                    .body(requestPlaylist)
                    .when()
                    .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                    .then()
                    .spec(responseSpecification)
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

        Playlist responsePlaylist = given(requestSpecification)
                .when()
                .get("/playlists/7bCg15yQ33NyMiTR7Hah3N")
                .then()
                .spec(responseSpecification)
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

        given(requestSpecification)
                .body(requestPlaylist)
                .when()
                .put("/playlists/7bCg15yQ33NyMiTR7Hah3N")
                .then()
                .spec(responseSpecification)
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


        Error errorResponse = given(requestSpecification)
                .body(requestPlaylist)
                .when()
                .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                .then()
                .spec(responseSpecification)
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
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .extract()
                .response()
                .as(Error.class);

        assertThat(errorResponse.getError().getStatus(),equalTo(401));
        assertThat(errorResponse.getError().getMessage(),equalTo("Invalid access token"));

    }


}
