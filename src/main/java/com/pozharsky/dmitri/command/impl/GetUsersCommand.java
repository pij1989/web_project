package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.Router;
import com.pozharsky.dmitri.command.SessionAttribute;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUsersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserService userService = UserServiceImpl.getInstance();
            List<User> users = userService.findAllUsers();
            //request.setAttribute(RequestAttribute.USERS, users);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USERS, users);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.USERS);
            return new Router(PagePath.USERS);
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
