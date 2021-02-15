package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.error.ApplicationError;
import com.pozharsky.dmitri.model.error.ErrorType;
import com.pozharsky.dmitri.model.service.impl.EmailServiceImpl;
import com.pozharsky.dmitri.model.service.impl.TokenServiceImpl;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            UserServiceImpl userService = UserServiceImpl.getInstance();
            EmailServiceImpl emailService = EmailServiceImpl.getInstance();
            TokenServiceImpl tokenService = TokenServiceImpl.getInstance();
            String firstName = request.getParameter(FIRST_NAME);
            String lastName = request.getParameter(LAST_NAME);
            String username = request.getParameter(USERNAME);
            String email = request.getParameter(EMAIL);
            String password = request.getParameter(PASSWORD);
            String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
            Map<String, String> userForm = new HashMap<>();
            userForm.put(FIRST_NAME, firstName);
            userForm.put(LAST_NAME, lastName);
            userForm.put(USERNAME, username);
            userForm.put(EMAIL, email);
            userForm.put(PASSWORD, password);
            userForm.put(CONFIRM_PASSWORD, confirmPassword);
            HttpSession session = request.getSession();
            if (userService.registrationUser(userForm)) {
                Optional<Token> optionalToken = tokenService.findTokenByUserEmail(email);
                if (optionalToken.isPresent()) {
                    Token token = optionalToken.get();
                    String tokenValue = token.getTokenValue();
                    emailService.sendActivationEmail(email, tokenValue);
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.ACTIVATE_REGISTRATION);
                    return new Router(PagePath.ACTIVATE_REGISTRATION);
                } else {
                    logger.info("Impossible activate registration");
                    session.setAttribute(SessionAttribute.ERROR_ACTIVATE_REGISTRATION, true);
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                    return new Router(PagePath.REGISTRATION, Router.Type.REDIRECT);
                }
            } else {
                ApplicationError applicationError = ApplicationError.getInstance();
                if (applicationError.getErrors().contains(ErrorType.ERROR_USER_EXIST)) {
                    logger.info("User with this email exist");
                    session.setAttribute(SessionAttribute.EMAIL, email);
                    session.setAttribute(SessionAttribute.ERROR_USER, true);
                    userForm.put(EMAIL, null);
                }
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                session.setAttribute(SessionAttribute.REGISTRATION_FORM, userForm);
                applicationError.clearErrors();
                return new Router(PagePath.REGISTRATION, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
