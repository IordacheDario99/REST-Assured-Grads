package com.endava.petclinic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvReader {
    private static Properties properties = new Properties();

    static {
        InputStream is = EnvReader.class.getClassLoader().getResourceAsStream("env.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUri() {
        return properties.getProperty("baseUri");
    }

    public static String getBasePath() {
        return properties.getProperty("basePath");
    }

    public static Integer getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public static Integer getSecurePort() {
        return Integer.parseInt(properties.getProperty("securePort"));
    }

    public static String getApiKey() {
        return properties.getProperty("apiKey");
    }

    public static String getApiKeySecret() {
        return properties.getProperty("apiKeySecret");
    }

    public static String getAccessToken() {
        return properties.getProperty("accessToken");
    }

    public static String getAccessTokenSecret() {
        return properties.getProperty("accessTokenSecret");
    }
    public static String getTwBanner() {
        return properties.getProperty("twBanner");
    }


}
