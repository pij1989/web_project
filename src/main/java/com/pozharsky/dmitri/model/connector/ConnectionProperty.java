package com.pozharsky.dmitri.model.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConnectionProperty {
    private static final Logger logger = LogManager.getLogger(ConnectionProperty.class);

    static Properties getProperties() {
        try (InputStream inputStream = ConnectionProperty.class.getClassLoader().getResourceAsStream(ConnectionParameter.DATABASE_PROPERTIES)) {
            Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(inputStream);
            }
            return properties;
        } catch (IOException e) {
            logger.error("Error of file: " + e);
            throw new RuntimeException(e);
        }
    }
}
