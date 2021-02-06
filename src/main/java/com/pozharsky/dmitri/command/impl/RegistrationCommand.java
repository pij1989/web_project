package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.service.impl.EmailServiceImpl;
import com.pozharsky.dmitri.model.service.impl.TokenServiceImpl;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserServiceImpl userService = UserServiceImpl.getInstance();
            EmailServiceImpl emailService = EmailServiceImpl.getInstance();
            TokenServiceImpl tokenService = TokenServiceImpl.getInstance();
            String firstName = request.getParameter(RequestParameter.FIRST_NAME);
            String lastName = request.getParameter(RequestParameter.LAST_NAME);
            String username = request.getParameter(RequestParameter.USERNAME);
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            List<String> errors = userService.checkEmailAndPassword(email, password);
            if (errors.isEmpty()) {
                if (userService.registrationUser(firstName, lastName, username, email, password)) {
                    Optional<Token> optionalToken = tokenService.findTokenByUserEmail(email);
                    if (optionalToken.isPresent()) {
                        Token token = optionalToken.get();
                        String tokenValue = token.getTokenValue();
                        emailService.sendActivationEmail(email, tokenValue);
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.ACTIVATE_REGISTRATION);
                        return new Router(PagePath.ACTIVATE_REGISTRATION);
                    } else {
                        logger.info("Impossible activate registration");
                        request.setAttribute(RequestAttribute.ERROR_ACTIVATE_REGISTRATION, true);
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                        return new Router(PagePath.REGISTRATION);
                    }
                } else {
                    logger.info("User with this email and password exist");
                    request.setAttribute(RequestAttribute.ERROR_USER, true);
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                    return new Router(PagePath.REGISTRATION);
                }
            } else {
                logger.info("Invalid email or password");
                request.setAttribute(RequestAttribute.ERROR_EMAIL_OR_PASSWORD, true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                return new Router(PagePath.REGISTRATION);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }
}
