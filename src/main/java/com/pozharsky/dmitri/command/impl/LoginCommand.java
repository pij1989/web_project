package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserService userService = UserServiceImpl.getInstance();
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            List<String> errors = userService.checkEmailAndPassword(email, password);
            if (errors.isEmpty()) {
                Optional<User> optionalUser = userService.loginUser(email, password);
                if (optionalUser.isPresent() && optionalUser.get().getStatusType().equals(StatusType.ACTIVE)) {
                    User user = optionalUser.get();
                    RoleType roleType = user.getRoleType();
                    session.setAttribute(SessionAttribute.USERNAME, user.getUsername());
                    session.setAttribute(SessionAttribute.ROLE, user.getRoleType().toString());
                    if (roleType.equals(RoleType.ADMIN)) {
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.ADMIN);
                        return new Router(PagePath.ADMIN);
                    } else {
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.MAIN);
                        return new Router(PagePath.MAIN);
                    }
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
