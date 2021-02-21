package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException{
        try {
            String email = request.getParameter(RequestParameter.EMAIL);
            String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
            String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
            UserService userService = UserServiceImpl.getInstance();
            HttpSession session = request.getSession();
            if (userService.changeUserPassword(email, oldPassword, newPassword)) {
                session.setAttribute(SessionAttribute.CHANGE_PASSWORD_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.CHANGE_PASSWORD_ERROR, true);
            }
            Router router = new Router(PagePath.CHANGE_PASSWORD, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
