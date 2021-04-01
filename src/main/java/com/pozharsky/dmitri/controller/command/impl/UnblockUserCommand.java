package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.TokenService;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.TokenServiceImpl;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UnblockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UnblockUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            TokenService tokenService = TokenServiceImpl.getInstance();
            UserService userService = UserServiceImpl.getInstance();
            String tokenValue = request.getParameter(RequestParameter.TOKEN);
            HttpSession session = request.getSession();
            logger.debug("Token: " + tokenValue);
            Optional<Token> optionalToken = tokenService.confirmToken(tokenValue);
            if (optionalToken.isPresent()) {
                Token token = optionalToken.get();
                long userId = token.getUserId();
                userService.changeUserStatus(userId, User.StatusType.ACTIVE);
            }
            Router router = new Router(PagePath.LOGIN, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
