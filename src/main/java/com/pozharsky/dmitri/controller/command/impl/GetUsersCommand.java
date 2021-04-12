package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
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

/**
 * Command for getting list of users.
 *
 * @author Dmitri Pozharsky
 */
public class GetUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUsersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            UserService userService = UserServiceImpl.getInstance();
            List<User> users = userService.findAllUsers();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USERS, users);
            Router router = new Router(PagePath.USERS);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
