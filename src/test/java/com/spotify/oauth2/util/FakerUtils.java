package com.spotify.oauth2.util;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateName(){
        Faker faker = new Faker();
        return "Playlist" +  faker.regexify("[A-Za-z0-9 ,_-]{20}");

    }

    public static String generateDesc(){
        Faker faker = new Faker();
        return "Description" +  faker.regexify("[A-Za-z0-9_@#$./-&]{50}");
    }
}
