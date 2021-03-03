package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.mail.MailSender;
import com.pozharsky.dmitri.model.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;

public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private static final String LINK = "http://localhost:8080/web_project_war/controller?command=activate_registration&token=";
    private static final String MAIL_SUBJECT = "Activate account";
    private static final EmailServiceImpl instance = new EmailServiceImpl();

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void sendActivationEmail(String sendToEmail, String token) throws ServiceException {
        try {
            String message = buildActivationHtmlMessage(token);
            MailSender mailSender = new MailSender(sendToEmail, MAIL_SUBJECT, message);
            mailSender.send();
        } catch (MessagingException e) {
            logger.error("Error generating or sending message", e);
            throw new ServiceException(e);
        }
    }

    private String buildActivationHtmlMessage(String token) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"").append(LINK).append(token).append("\">Activate now</a>\n");
        builder.append("<p>Link will expire in 15 minutes</p>");
        return builder.toString();
    }
}
