package com.pozharsky.dmitri.model.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger(ConnectionCreator.class);
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";

    static Connection createConnection() {
        try {
            Properties properties = getProperties();
            String driver = properties.getProperty(DRIVER);
            String url = properties.getProperty(URL);
            String username = properties.getProperty(USERNAME);
            String password = properties.getProperty(PASSWORD);
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties() {
        try (InputStream inputStream = ConnectionCreator.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(inputStream));
            return properties;
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
