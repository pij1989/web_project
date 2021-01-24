package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.model.mail.MailSender;
import com.pozharsky.dmitri.model.service.EmailService;

public class EmailServiceImpl implements EmailService {

    @Override
    public void sendActivationEmail(String sendToEmail, String link) {
        String message = buildActivationHtmlMessage(link);
        MailSender mailSender = new MailSender(sendToEmail, "Activate account", message);
        mailSender.send();
    }

    private String buildActivationHtmlMessage(String link) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  </head>\n" +
                "  <body style=\"font-family: sans-serif;\">\n" +
                "<p>Thank you for registration. Please click on the below link to activate your account:</p>\n" +
                "<a href=\"" + link + "\">Activate now</a>\n" +
                "<p>Link will expire in 15 minutes</p>\n" +
                "  </body>\n" +
                "</html>";
    }
}
