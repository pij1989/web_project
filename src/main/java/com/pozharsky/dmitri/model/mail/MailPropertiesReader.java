package com.pozharsky.dmitri.model.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class MailPropertiesReader {
    private static final Logger logger = LogManager.getLogger(MailPropertiesReader.class);

    static Properties readProperties(String mailProperties) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = MailPropertiesReader.class.getClassLoader().getResourceAsStream(mailProperties);
            if (inputStream != null) {
                properties.load(inputStream);
            }
            return properties;
        } catch (IOException e) {
            logger.error("Can not read load properties from properties file: " + e);
            throw new RuntimeException(e);
        }
    }
}
