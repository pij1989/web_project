package com.pozharsky.dmitri.command.impl.pagecommand;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.RequestAttribute;
import com.pozharsky.dmitri.command.Router;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToUsersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ToUsersPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            UserService userService = UserServiceImpl.getInstance();
            List<User> users = userService.findAllUsers();
            request.setAttribute(RequestAttribute.USERS, users);
            return new Router(PagePath.ADMIN);
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
