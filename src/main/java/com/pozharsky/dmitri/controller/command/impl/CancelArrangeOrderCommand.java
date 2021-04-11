package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for canceling arranging of order.
 *
 * @author Dmitri Pozharsky
 */
public class CancelArrangeOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
        Order.StatusType statusType = order.getStatusType();
        if (statusType == Order.StatusType.PROCESSING) {
            order.setStatusType(Order.StatusType.NEW);
        }
        Router router = new Router(PagePath.VIEW_CART);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
        return router;
    }
}
