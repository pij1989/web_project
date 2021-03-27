package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewCartCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewCartCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
            OrderService orderService = OrderServiceImpl.getInstance();
            List<OrderProduct> orderProducts = new ArrayList<>();
            if (order != null) {
                orderProducts = orderService.findProductInOrder(order.getId());
            } else {
                User user = (User) session.getAttribute(SessionAttribute.USER);
                Optional<Order> optionalOrder = orderService.findNewOrder(user.getId());
                if (optionalOrder.isPresent()) {
                    order = optionalOrder.get();
                    orderProducts = orderService.findProductInOrder(order.getId());
                    session.setAttribute(SessionAttribute.ORDER, order);
                } else {
                    request.setAttribute(RequestAttribute.ORDER_IS_EMPTY, true);
                }
            }
            session.setAttribute(SessionAttribute.ORDER_PRODUCTS, orderProducts);
            Router router = new Router(PagePath.VIEW_CART);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (Exception e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
