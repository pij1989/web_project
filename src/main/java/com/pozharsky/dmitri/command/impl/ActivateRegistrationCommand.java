package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.Token;
import com.pozharsky.dmitri.model.service.TokenService;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.TokenServiceImpl;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ActivateRegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivateRegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
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
                userService.changeUserStatus(userId, StatusType.ACTIVE);
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
                return new Router(PagePath.LOGIN, Router.Type.REDIRECT);
            } else {
                session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.REGISTRATION);
                return new Router(PagePath.REGISTRATION, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException();
        }
    }
}