package com.andrey.application.repository.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties(String path) {
        Properties configuration = new Properties();
        try(InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(path)) {
            configuration.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Property file wasn't found or profile option is not specified.");
        }
        return configuration;
    }
}
