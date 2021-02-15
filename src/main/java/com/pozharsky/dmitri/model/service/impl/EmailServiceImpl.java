package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.model.mail.MailSender;
import com.pozharsky.dmitri.model.service.EmailService;

public class EmailServiceImpl implements EmailService {
    private static final String LINK = "http://localhost:8080/web_project_war/controller?command=activate_registration&token=";
    private static final String MAIL_SUBJECT = "Activate account";
    private static final EmailServiceImpl instance = new EmailServiceImpl();

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void sendActivationEmail(String sendToEmail, String token) {
        String message = buildActivationHtmlMessage(token);
        MailSender mailSender = new MailSender(sendToEmail, MAIL_SUBJECT, message);
        mailSender.send();
    }

    private String buildActivationHtmlMessage(String token) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"").append(LINK).append(token).append("\">Activate now</a>\n");
        builder.append("<p>Link will expire in 15 minutes</p>");
        return builder.toString();
    }
}
