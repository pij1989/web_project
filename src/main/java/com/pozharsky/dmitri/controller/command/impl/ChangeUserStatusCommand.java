package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command for changing user's status.
 *
 * @author Dmitri Pozharsky
 */
public class ChangeUserStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeUserStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String userId = request.getParameter(RequestParameter.USER_ID);
            String status = request.getParameter(RequestParameter.STATUS);
            HttpSession session = request.getSession();
            UserService userService = UserServiceImpl.getInstance();
            long id = Long.parseLong(userId);
            User.StatusType statusType = User.StatusType.valueOf(status);
            boolean result = userService.changeUserStatus(id, statusType);
            if (result) {
                @SuppressWarnings("unchecked")
                List<User> users = (List<User>) session.getAttribute(SessionAttribute.USERS);
                List<User> updateUsers = users.stream()
                        .peek(user -> {
                            if (user.getId() == id) {
                                user.setStatusType(statusType);
                            }
                        })
                        .collect(Collectors.toList());
                session.setAttribute(SessionAttribute.USERS, updateUsers);
                session.setAttribute(SessionAttribute.CHANGE_STATUS_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.CHANGE_STATUS_ERROR, true);
            }
            Router router = new Router(PagePath.USERS, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
