package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String orderId = request.getParameter(RequestParameter.ORDER_ID);
            HttpSession session = request.getSession();
            long id = Long.parseLong(orderId);
            OrderService orderService = OrderServiceImpl.getInstance();
            if (orderService.deleteOrder(id)) {
                @SuppressWarnings("unchecked")
                List<Order> orders = (List<Order>) session.getAttribute(SessionAttribute.ORDERS);
                orders = orders.stream()
                        .filter(e -> e.getId() != id)
                        .collect(Collectors.toList());
                session.setAttribute(SessionAttribute.ORDERS, orders);
                session.setAttribute(SessionAttribute.DELETE_ORDER_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.DELETE_ORDER_ERROR, true);
            }
            Router router = new Router(PagePath.ORDERS_ADMIN, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
