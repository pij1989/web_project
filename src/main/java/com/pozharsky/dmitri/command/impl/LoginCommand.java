package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.error.ApplicationError;
import com.pozharsky.dmitri.model.error.ErrorType;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserService userService = UserServiceImpl.getInstance();
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            AtomicInteger blockingCount = (AtomicInteger) session.getAttribute(SessionAttribute.BLOCKING_COUNT);
            Optional<User> optionalUser = userService.loginUser(email, password, blockingCount);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                StatusType status = user.getStatusType();
                if (status.equals(StatusType.ACTIVE)) {
                    RoleType roleType = user.getRoleType();
                    session.setAttribute(SessionAttribute.USERNAME, user.getUsername());
                    session.setAttribute(SessionAttribute.ROLE, user.getRoleType());
                    if (roleType.equals(RoleType.ADMIN)) {
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.ADMIN);
                        return new Router(PagePath.ADMIN);
                    } else {
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.MAIN);
                        return new Router(PagePath.MAIN);
                    }
                } else {
                    if (status.equals(StatusType.BLOCKED)) {
                        session.setAttribute(SessionAttribute.BLOCKED_USER, true);
                    }
                    session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                    return new Router(PagePath.LOGIN, Router.Type.REDIRECT);
                }
            } else {
                ApplicationError applicationError = ApplicationError.getInstance();
                if (applicationError.hasErrors()) {
                    List<ErrorType> errors = applicationError.getErrors();
                    session.setAttribute(SessionAttribute.ERRORS, errors);
                    applicationError.clearErrors();
                } else {
                    if (!userService.blockUser(email, blockingCount)) {
                        session.setAttribute(SessionAttribute.MISMATCHED_PASSWORD, true);
                    } else {
                        session.setAttribute(SessionAttribute.BLOCKED_USER, true);
                    }
                }
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                return new Router(PagePath.LOGIN, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }
}
