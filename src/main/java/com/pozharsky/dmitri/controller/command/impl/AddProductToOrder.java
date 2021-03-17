package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class AddProductToOrder implements Command {
    private static final Logger logger = LogManager.getLogger(AddProductToOrder.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String amountProduct = request.getParameter(RequestParameter.AMOUNT_PRODUCT);
            HttpSession session = request.getSession();
            Product product = (Product) session.getAttribute(SessionAttribute.PRODUCT);
            User user = (User) session.getAttribute(SessionAttribute.USER);
            long userId = user.getId();
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<Order> findOptionalOrder = orderService.findNewOrder(userId);
            Router router = new Router(PagePath.VIEW_PRODUCT, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            boolean isAdd;
            Order order;
            if (findOptionalOrder.isPresent()) {
                order = findOptionalOrder.get();
            } else {
                Optional<Order> optionalOrder = orderService.addNewOrder(userId);
                if (optionalOrder.isPresent()) {
                    order = optionalOrder.get();
                } else {
                    session.setAttribute(SessionAttribute.CREATE_ORDER_ERROR, true);
                    return router;
                }
            }
            isAdd = orderService.addProductToOrder(amountProduct, product, order);
            if (isAdd) {
                List<OrderProduct> orderProducts = orderService.findProductInNewOrder(order.getId());
                session.setAttribute(SessionAttribute.ORDER_PRODUCTS, orderProducts);
            }
            //TODO:problem with order
            session.setAttribute(SessionAttribute.ORDER, order);
            session.setAttribute(SessionAttribute.ADD_ORDER_SUCCESS, isAdd);
            return router;
        } catch (Exception e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
