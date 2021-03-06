package com.pozharsky.dmitri.model.mail;

import com.pozharsky.dmitri.exception.MailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);
    private static final String MAIL_PROPERTIES = "mail.properties";
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailSender(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.properties = MailPropertiesReader.readProperties(MAIL_PROPERTIES);
    }

    public void send() throws MailException {
        try {
            initMessage();
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Error generating or sending message", e);
            throw new MailException(e);
        }
    }

    private void initMessage() throws MessagingException {
        Session mailSession = MailSessionCreator.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText, "text/html");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
    }
}
