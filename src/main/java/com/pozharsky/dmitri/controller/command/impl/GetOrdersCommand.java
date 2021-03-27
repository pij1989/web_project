package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetOrdersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);
            OrderService orderService = OrderServiceImpl.getInstance();
            List<Order> orders = orderService.findNotNewOrders(user.getId());
            session.setAttribute(SessionAttribute.ORDERS, orders);
            Router router = new Router(PagePath.ORDERS);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
