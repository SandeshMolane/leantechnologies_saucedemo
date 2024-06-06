package com.leantechnologies.saucedemo.pagesandservices.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsUtil {
    private static Properties credentialsProps;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/credentials.properties");
            credentialsProps = new Properties();
            credentialsProps.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Error reading credentials.properties file: " + e.getMessage());
        }
    }

    public static String getUsername() {
        return credentialsProps.getProperty("username");
    }

    public static String getPassword() {
        return credentialsProps.getProperty("password");
    }
}