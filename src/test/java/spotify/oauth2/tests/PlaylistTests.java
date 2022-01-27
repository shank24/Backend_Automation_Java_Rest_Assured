package spotify.oauth2.tests;

import io.restassured.RestAssured;
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
    String access_Token = "BQCNZiiQcbA6tykY07R6r0KdcqgTlPt0pm0IM4zdiolf0whRcwFxZdkSioaloALElr6SqvJD_H6WWyi_1_DFjSEQVIhaJpBjpLPJnf0T1cxYKvqBb1I0wJwxvdiABY28lPIJL9MJSUgoIYn3YFQBWwbWkv2RLz_3FChSCcCk79Rpl2FwMhjIx6-yJ-cKi2iVpqW19KXwnKdNNg4WiJAqHq3GrhwgxHdFNlYOtwYr_c3c-omg";

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
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void shouldBeAbleToCreatePlaylist(){
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
}
