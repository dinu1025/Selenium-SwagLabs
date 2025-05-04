package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilsConfig {

    public Properties getProperties(String filename) {
        // Use ClassLoader to load the properties file from resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename + ".properties");

        if (inputStream == null) {
            throw new RuntimeException("Properties file not found!");
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
