package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Delivery;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ViewOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String stringOrderId = request.getParameter(RequestParameter.ORDER_ID);
        long orderId;
        try {
            orderId = Long.parseLong(stringOrderId);
        } catch (NumberFormatException e) {
            logger.error("Incorrect order id", e);
            return new Router(PagePath.ERROR_404, Router.Type.REDIRECT);
        }
        try {
            HttpSession session = request.getSession();
            User.RoleType roleType = (User.RoleType) session.getAttribute(SessionAttribute.ROLE);
            OrderService orderService = OrderServiceImpl.getInstance();
            List<OrderProduct> orderProducts = orderService.findProductInOrder(orderId);
            Optional<Order> optionalOrder = orderService.findOrderById(orderId);
            Router router = null;
            if (roleType == User.RoleType.USER) {
                if (optionalOrder.isPresent()) {
                    Order order = optionalOrder.get();
                    request.setAttribute(RequestAttribute.VIEW_ORDER, order);
                }
                router = new Router(PagePath.VIEW_ORDER_USER);
            } else {
                if (roleType == User.RoleType.ADMIN) {
                    if (optionalOrder.isPresent()) {
                        Order order = optionalOrder.get();
                        Optional<Delivery> optionalDelivery = orderService.findOrderDelivery(orderId);
                        UserService userService = UserServiceImpl.getInstance();
                        Optional<User> optionalUser = userService.findUserById(order.getUserId());
                        if (optionalDelivery.isPresent()) {
                            Delivery delivery = optionalDelivery.get();
                            request.setAttribute(RequestAttribute.DELIVERY, delivery);
                        }
                        if (optionalUser.isPresent()) {
                            User user = optionalUser.get();
                            request.setAttribute(RequestAttribute.VIEW_USER, user);
                        }
                        request.setAttribute(RequestAttribute.VIEW_ORDER, order);
                    }
                    router = new Router(PagePath.VIEW_ORDER_ADMIN);
                }
            }
            request.setAttribute(RequestAttribute.ORDER_PRODUCTS, orderProducts);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
