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

/**
 * Command for changing status of order.
 *
 * @author Dmitri Pozharsky
 */
public class ChangeOrderStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeOrderStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String orderStatus = request.getParameter(RequestParameter.ORDER_STATUS);
            String orderId = request.getParameter(RequestParameter.ORDER_ID);
            HttpSession session = request.getSession();
            long id = Long.parseLong(orderId);
            Order.StatusType status = Order.StatusType.valueOf(orderStatus);
            OrderService orderService = OrderServiceImpl.getInstance();
            if (orderService.changeOrderStatus(id, status)) {
                @SuppressWarnings("unchecked")
                List<Order> orders = (List<Order>) session.getAttribute(SessionAttribute.ORDERS);
                orders = orders.stream()
                        .peek(e -> {
                            if (e.getId() == id) {
                                e.setStatusType(status);
                            }
                        }).collect(Collectors.toList());
                session.setAttribute(SessionAttribute.ORDERS, orders);
                session.setAttribute(SessionAttribute.CHANGE_STATUS_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.CHANGE_STATUS_ERROR, true);
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
