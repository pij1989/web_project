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
import java.util.Map;
import java.util.Optional;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

/**
 * Command for user registration.
 *
 * @author Dmitri Pozharsky
 */
public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            UserServiceImpl userService = UserServiceImpl.getInstance();
            EmailServiceImpl emailService = EmailServiceImpl.getInstance();
            TokenServiceImpl tokenService = TokenServiceImpl.getInstance();
            Map<String, String> userForm = requestParameterToMap(request, FIRST_NAME, LAST_NAME, USERNAME,
                    EMAIL, PASSWORD, CONFIRM_PASSWORD);
            String email = request.getParameter(EMAIL);
            HttpSession session = request.getSession();
            if (userService.registrationUser(userForm)) {
                Optional<Token> optionalToken = tokenService.findTokenByUserEmail(email);
                if (optionalToken.isPresent()) {
                    Token token = optionalToken.get();
                    String tokenValue = token.getTokenValue();
                    emailService.sendActivationEmail(email, tokenValue);
                    Router router = new Router(PagePath.ACTIVATE_REGISTRATION);
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
                    return router;
                } else {
                    logger.info("Impossible activate registration");
                    session.setAttribute(SessionAttribute.ERROR_ACTIVATE_REGISTRATION, true);
                }
            } else {
                ApplicationError applicationError = ApplicationError.getInstance();
                if (applicationError.getErrors().contains(ErrorType.ERROR_USER_EXIST)) {
                    logger.info("User with this email exist");
                    session.setAttribute(SessionAttribute.EMAIL, email);
                    session.setAttribute(SessionAttribute.ERROR_USER, true);
                    userForm.put(EMAIL, null);
                }
                session.setAttribute(SessionAttribute.REGISTRATION_FORM, userForm);
                applicationError.clearErrors();
            }
            Router router = new Router(PagePath.REGISTRATION, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
