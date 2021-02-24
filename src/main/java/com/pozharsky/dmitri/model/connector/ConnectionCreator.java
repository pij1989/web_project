package com.pozharsky.dmitri.model.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger(ConnectionCreator.class);
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";
    private static Properties properties;

    static {
        try {
            properties = getProperties();
            String driver = properties.getProperty(DRIVER);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.fatal("Impossible registration driver", e);
            throw new RuntimeException(e);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() throws SQLException {
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        return DriverManager.getConnection(url, username, password);
    }

    private static Properties getProperties() {
        try (InputStream inputStream = ConnectionCreator.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)) {
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
