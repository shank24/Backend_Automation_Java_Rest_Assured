package spotify.oauth2.tests;

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
    String access_Token = "BQDAMmyJEVLS3MG807y8vbw_fX_wovBwQ8FOxEMa5o0rPUdlW-OkkQd02Ska9YJUGUFHrEW_2tfZMEsnNiL93QvxZYmJCswoLjLyvqyHCtR6rbC6mlGg4Oo3tdaa-CB2yMJyDPce82KI3BvOP2RDu6uMZDJf7knjh3kyij0mk5dKcsxlliMz-ic-LkFyR0EDWYwt_Zg9fKlkKYbJxnTLPwkdyfBpdiaSWL7h6JPma7uWDtkr";

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
     * Get A Playlist
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

}
