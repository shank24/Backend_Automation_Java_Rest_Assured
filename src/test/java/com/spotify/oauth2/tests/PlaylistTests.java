package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_Token = "BQCOvPO084L9guPGYhlO5xMFLw36wVSoZ9CDYRhawR7m5ZxkfEKe9FSlH8RkpW9tOzaJzIq2laB4i2FV3J33HpnopfSB5KLLFON891pKsXu_h1Mgr8QAl8JXcGzDkcroXsGV1ZIku7Qo6rlcOrJBk--StEyVY86-3YT9VQvoFY7oQoPc2LfpONvUUY4Z8o3wpKFTfbFonYxXvnjkt9fzWHIAUh6nZJ6i2t17aaZ-G0ruYqZx";

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
            String payload = "{\n" +
                    "  \"name\": \"My Playlist\",\n" +
                    "  \"description\": \"Jazz\",\n" +
                    "  \"public\": false\n" +
                    "}";

            given(requestSpecification)
                    .body(payload)
                    .when()
                    .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                    .then()
                    .spec(responseSpecification)
                    .assertThat()
                    .statusCode(201)
                    .body("name",equalTo("My Playlist"),
                            "description",equalTo("Jazz"),
                            "public",equalTo(false));
    }

    /**
     * Get A Playlist
     */
    @Test
    public void shouldBeAbleToFetchPlaylist(){
        given(requestSpecification)
                .when()
                .get("/playlists/7bCg15yQ33NyMiTR7Hah3N")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    /**
     * Update A Playlist
     */
    @Test
    public void shouldBeAbleToUpdatePlaylist(){

        String payload = "{\n" +
                "  \"name\": \"Updated Playlist Name\",\n" +
                "  \"description\": \"Updated playlist description\",\n" +
                "  \"public\": false\n" +
                "}";

        given(requestSpecification)
                .body(payload)
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
        String payload = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"Jazz\",\n" +
                "  \"public\": false\n" +
                "}";

        given(requestSpecification)
                .body(payload)
                .when()
                .post("/users/31uxnqcuy7hrnntpso5yaoe56uzy/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .body("error.status",equalTo(400),
                        "error.message",equalTo("Missing required field: name"));
    }


}
