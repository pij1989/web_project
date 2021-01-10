package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserServiceImpl userService = UserServiceImpl.INSTANCE;
            String firstName = request.getParameter(RequestParameter.FIRST_NAME);
            String lastName = request.getParameter(RequestParameter.LAST_NAME);
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            List<String> errors = userService.checkEmailAndPassword(email, password);
            if (errors.isEmpty()) {
                if (userService.createUser(firstName, lastName, email, password)) {
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                    return new Router(PagePath.LOGIN, RouterType.REDIRECT);
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
