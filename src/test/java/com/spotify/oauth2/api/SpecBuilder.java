package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    static String access_Token = "BQCJpB1NjoRmIVNDsSGgHj0V6CS7KGa7hEtqr18ca1S3CrSRJztbZu71XNZd670ElyTRB5rDjKBscSWp0WXO9wQsJuD4DnLlBkHKBAl11dcAKO0yVGogNFSbss1yDuQ1Sbxcn9aWJDw4Tqn0zoaEG4JvlJ_ejycxWULVWz0jQ3waJY_L3aBmnsw9hPHypitfTH2AcgDwBv3v6_4OsS5U_MMl6vd_PAdj-6g3Zv5PJ6irJjYV";

    public static RequestSpecification getRequestSpec(){

        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization","Bearer " + access_Token)
                //requestSpecBuilder.addHeader("x-api-key", ObjectReader.reader.getKey());
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(){

        return new ResponseSpecBuilder()
                //.expectStatusCode(201)
                //.expectContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
    }
}
