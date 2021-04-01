package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.*;
import com.pozharsky.dmitri.model.error.ApplicationError;
import com.pozharsky.dmitri.model.error.ErrorType;
import com.pozharsky.dmitri.model.service.*;
import com.pozharsky.dmitri.model.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final int LIMIT = 6;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            UserService userService = UserServiceImpl.getInstance();
            String email = request.getParameter(RequestParameter.EMAIL);
            String password = request.getParameter(RequestParameter.PASSWORD);
            HttpSession session = request.getSession();
            AtomicInteger blockingCount = (AtomicInteger) session.getAttribute(SessionAttribute.BLOCKING_COUNT);
            Optional<User> optionalUser = userService.loginUser(email, password, blockingCount);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                User.StatusType status = user.getStatusType();
                if (status == User.StatusType.ACTIVE) {
                    User.RoleType roleType = user.getRoleType();
                    session.setAttribute(SessionAttribute.USER, user);
                    session.setAttribute(SessionAttribute.ROLE, user.getRoleType());
                    if (roleType == User.RoleType.ADMIN) {
                        Router router = new Router(PagePath.ADMIN, Router.Type.REDIRECT);
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
                        return router;
                    }
                    if (roleType == User.RoleType.USER) {
                        ProductService productService = ProductServiceImpl.getInstance();
                        CategoryService categoryService = CategoryServiceImpl.getInstance();
                        OrderService orderService = OrderServiceImpl.getInstance();
                        List<Product> products = productService.findLastAddActiveProduct(LIMIT);
                        List<Category> categories = categoryService.findAllCategory();
                        Optional<Order> optionalOrder = orderService.findNewOrder(user.getId());
                        if (optionalOrder.isPresent()) {
                            Order order = optionalOrder.get();
                            List<OrderProduct> orderProducts = orderService.findProductInOrder(order.getId());
                            session.setAttribute(SessionAttribute.ORDER, order);
                            session.setAttribute(SessionAttribute.ORDER_PRODUCTS, orderProducts);
                        }
                        session.setAttribute(SessionAttribute.LAST_PRODUCTS, products);
                        session.setAttribute(SessionAttribute.CATEGORIES, categories);
                        Router router = new Router(PagePath.MAIN, Router.Type.REDIRECT);
                        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
                        return router;
                    }
                } else {
                    if (status == User.StatusType.BLOCKED) {
                        session.setAttribute(SessionAttribute.BLOCKED_USER, true);
                    }
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
                        TokenService tokenService = TokenServiceImpl.getInstance();
                        Optional<Token> optionalToken = tokenService.findTokenByUserEmail(email);
                        if (optionalToken.isPresent()) {
                            Token token = optionalToken.get();
                            EmailService emailService = EmailServiceImpl.getInstance();
                            emailService.sendUnblockingEmail(email, token.getTokenValue());
                        }
                        session.setAttribute(SessionAttribute.BLOCKED_USER, true);
                    }
                }
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
