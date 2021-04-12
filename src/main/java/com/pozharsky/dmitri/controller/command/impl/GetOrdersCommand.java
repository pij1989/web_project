package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Command for getting list of user's orders.
 *
 * @author Dmitri Pozharsky
 */
public class GetOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetOrdersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);
            User.RoleType roleType = user.getRoleType();
            OrderService orderService = OrderServiceImpl.getInstance();
            List<Order> orders = new ArrayList<>();
            Router router = null;
            if (roleType == User.RoleType.USER) {
                orders = orderService.findNotNewOrders(user.getId());
                router = new Router(PagePath.ORDERS_USER);
            } else {
                if (roleType == User.RoleType.ADMIN) {
                    orders = orderService.findAllOrders();
                    router = new Router(PagePath.ORDERS_ADMIN);
                }
            }
            session.setAttribute(SessionAttribute.ORDERS, orders);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
