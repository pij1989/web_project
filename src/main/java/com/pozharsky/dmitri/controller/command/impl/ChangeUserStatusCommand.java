package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeUserStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeUserStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String userId = request.getParameter(RequestParameter.USER_ID);
            String status = request.getParameter(RequestParameter.STATUS);
            HttpSession session = request.getSession();
            logger.debug("User id: " + userId + " Status: " + status);
            UserService userService = UserServiceImpl.getInstance();
            boolean result = userService.changeUserStatus(Long.parseLong(userId), StatusType.valueOf(status));
            if (result) {
                @SuppressWarnings("unchecked")
                List<User> users = (List<User>) session.getAttribute(SessionAttribute.USERS);
                List<User> updateUsers = users.stream()
                        .peek(user -> {
                            if (user.getId() == Long.parseLong(userId)) {
                                user.setStatusType(StatusType.valueOf(status));
                            }
                        })
                        .collect(Collectors.toList());
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.USERS);
                session.setAttribute(SessionAttribute.USERS, updateUsers);
                session.setAttribute(SessionAttribute.CHANGE_STATUS, "Success");
                return new Router(PagePath.USERS, Router.Type.REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.CHANGE_STATUS, "Error");
                return new Router(PagePath.USERS, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
