package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AddProductToCart implements Command {
    private static final Logger logger = LogManager.getLogger(AddProductToCart.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String amountProduct = request.getParameter(RequestParameter.AMOUNT_PRODUCT);
            HttpSession session = request.getSession();
            Product product = (Product) session.getAttribute(SessionAttribute.PRODUCT);
            User user = (User) session.getAttribute(SessionAttribute.USER);
            long userId = user.getId();
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<Order> optionalOrder = orderService.findNewOrder(userId);
            boolean isAdd;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                isAdd = orderService.addProductToOrder(amountProduct, product, order);
            } else {
                isAdd = orderService.addNewOrder(amountProduct, product, userId);
            }
            session.setAttribute(SessionAttribute.ADD_ORDER_SUCCESS, isAdd);
            Router router = new Router(PagePath.VIEW_PRODUCT, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (Exception e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
