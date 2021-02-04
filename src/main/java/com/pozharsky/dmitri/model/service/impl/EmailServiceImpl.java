package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.model.mail.MailSender;
import com.pozharsky.dmitri.model.service.EmailService;

public class EmailServiceImpl implements EmailService {
    private static final String LINK = "http://localhost:8080/web_project_war/controller?command=activate_registration&token=";
    private static final EmailServiceImpl instance = new EmailServiceImpl();

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void sendActivationEmail(String sendToEmail, String token) {
        String message = buildActivationHtmlMessage(token);
        MailSender mailSender = new MailSender(sendToEmail, "Activate account", message);
        mailSender.send();
    }

    private String buildActivationHtmlMessage(String token) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  </head>\n" +
                "  <body style=\"font-family: sans-serif;\">\n" +
                "<p>Thank you for registration. Please click on the below link to activate your account:</p>\n" +
                "<a href=\"" + LINK + token + "\">Activate now</a>\n" +
                "<p>Link will expire in 15 minutes</p>\n" +
                "  </body>\n" +
                "</html>";
    }
}
