package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.MailException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.mail.MailSender;
import com.pozharsky.dmitri.model.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private static final String ACTIVATE_LINK = "http://localhost:8080/web_project_war/controller?command=activate_registration&token=";
    private static final String UNBLOCK_LINK = "http://localhost:8080/web_project_war/controller?command=unblock_user&token=";
    private static final String ACTIVATE_MAIL_SUBJECT = "Activate account";
    private static final String UNBLOCK_MAIL_SUBJECT = "Unblock account";
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
            MailSender mailSender = new MailSender(sendToEmail, ACTIVATE_MAIL_SUBJECT, message);
            mailSender.send();
        } catch (MailException e) {
            logger.error("Impossible send activation link to email", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendUnblockingEmail(String sendToEmail, String token) throws ServiceException {
        try {
            String message = buildUnblockingHtmlMessage(token);
            MailSender mailSender = new MailSender(sendToEmail, UNBLOCK_MAIL_SUBJECT, message);
            mailSender.send();
        } catch (MailException e) {
            logger.error("Impossible send unblocking link to email", e);
            throw new ServiceException(e);
        }
    }

    private String buildActivationHtmlMessage(String token) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"").append(ACTIVATE_LINK).append(token).append("\">Activate now</a>\n");
        builder.append("<p>Link will expire in 15 minutes</p>");
        return builder.toString();
    }

    private String buildUnblockingHtmlMessage(String token) {
        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"").append(UNBLOCK_LINK).append(token).append("\">Unblock user now</a>\n");
        builder.append("<p>Link will expire in 15 minutes</p>");
        return builder.toString();
    }
}
