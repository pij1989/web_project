package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.entity.RoleType;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.service.UserService;
import com.pozharsky.dmitri.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserService userService = UserServiceImpl.INSTANCE;
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            List<String> errors = userService.checkEmailAndPassword(email, password);
            if (errors.isEmpty()) {
                if (userService.checkUser(email, password)) {
                    session.setAttribute(SessionAttribute.ROLE, RoleType.USER.toString());
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.MAIN);
                    return new Router(PagePath.MAIN);
                } else {
                    logger.info("User with this email and password can not exist");
                    request.setAttribute(RequestAttribute.ERROR_USER, true);
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                    return new Router(PagePath.LOGIN);
                }
            } else {
                logger.info("Invalid email or password");
                request.setAttribute(RequestAttribute.ERROR_EMAIL_OR_PASSWORD, true);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                return new Router(PagePath.LOGIN);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }
}
