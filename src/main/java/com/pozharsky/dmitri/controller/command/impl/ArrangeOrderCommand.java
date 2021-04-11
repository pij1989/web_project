package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for arranging user's order.
 *
 * @author Dmitri Pozharsky
 */
public class ArrangeOrderCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
        Order.StatusType statusType = order.getStatusType();
        if (statusType == Order.StatusType.NEW) {
            order.setStatusType(Order.StatusType.PROCESSING);
        }
        Router router = new Router(PagePath.ARRANGE_ORDER);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
        return router;
    }
}
