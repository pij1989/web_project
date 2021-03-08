package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.error.ApplicationError;
import com.pozharsky.dmitri.model.error.ErrorType;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

public class CreateUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            UserServiceImpl userService = UserServiceImpl.getInstance();
            Map<String, String> userForm = requestParameterToMap(request, FIRST_NAME, LAST_NAME, USERNAME, EMAIL, ROLE,
                    STATUS, PASSWORD, CONFIRM_PASSWORD);
            HttpSession session = request.getSession();
            if (userService.createUserByAdmin(userForm)) {
                session.setAttribute(SessionAttribute.CREATE_USER_SUCCESS, true);
            } else {
                ApplicationError applicationError = ApplicationError.getInstance();
                if (applicationError.getErrors().contains(ErrorType.ERROR_USER_EXIST)) {
                    logger.info("User with this email exist");
                    userForm.put(EMAIL, null);
                }
                applicationError.clearErrors();
                session.setAttribute(SessionAttribute.CREATE_USER_ERROR, true);
                session.setAttribute(SessionAttribute.REGISTRATION_FORM, userForm);
            }
            Router router = new Router(PagePath.CREATE_USER, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
