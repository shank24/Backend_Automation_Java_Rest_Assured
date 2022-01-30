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
    String access_Token = "BQCxrTgZcj9fIWq-Yhqlg4zMM2zLlDODe0aHqXO7zIDan8HqHnBYH_0DQyMDzMSKv0LedAo81Sh7tuheM22laXo6IGemLOYOVX4CSuBycTvbvWnHY6pqQ8oTz4jKPHlRifc1xJPJPFw5OIhI1jBgGCL9HrQM-DT-ECiADkvhU3CXK22ZiFmR2FpiD3IV2KwEroTchyJzKtdjcN8Pvj6CTV9YQIcjsyWun-ynwvk0XzSn19GZ";

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
        Playlist requestPlaylist = new Playlist();

        requestPlaylist.setName("New Playlist");
        requestPlaylist.setDescription("Blues");
        requestPlaylist.setPublic(false);



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


        Playlist requestPlaylist = new Playlist();

        requestPlaylist.setName("Updated New Playlist");
        requestPlaylist.setDescription("Updated  Reggae");
        requestPlaylist.setPublic(false);


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

        Playlist requestPlaylist = new Playlist();

        requestPlaylist.setName("Updated New Playlist");
        requestPlaylist.setDescription("Updated  Reggae");
        requestPlaylist.setPublic(false);

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
        Playlist requestPlaylist = new Playlist();

        requestPlaylist.setName("");
        requestPlaylist.setDescription("Updated  Reggae");
        requestPlaylist.setPublic(false);


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
        Playlist requestPlaylist = new Playlist();

        requestPlaylist.setName("");
        requestPlaylist.setDescription("Updated  Reggae");
        requestPlaylist.setPublic(false);


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
