package com.pozharsky.dmitri.model.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

class MailSessionCreator {
    private static final String USERNAME = "mail.user.name";
    private static final String PASSWORD = "mail.user.password";

    static Session createSession(Properties configProperties) {
        String username = configProperties.getProperty(USERNAME);
        String password = configProperties.getProperty(PASSWORD);
        return Session.getDefaultInstance(configProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
