package com.spotify.oauth2.util;

import java.io.*;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath) {

        Properties properties = new Properties();
        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader(filePath));
                try {
                    properties.load(reader);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to load properties file " + filePath);
                }
        }

        catch (FileNotFoundException f) {
            f.printStackTrace();
            throw new RuntimeException("properties file not found at " + filePath);
        }

        return properties;
    }
}
