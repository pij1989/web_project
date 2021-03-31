package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

public class ConfirmOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ConfirmOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Map<String, String> deliveryForm = requestParameterToMap(request, FIRST_NAME, LAST_NAME, CITY, STREET, HOME_NUMBER, PHONE);
            logger.debug("First name: " + deliveryForm.get(FIRST_NAME) + " Last name: " + deliveryForm.get(LAST_NAME) +
                    " City: " + deliveryForm.get(CITY) + " Street: " + deliveryForm.get(STREET) +
                    " Home number: " + deliveryForm.get(HOME_NUMBER) + " Phone: " + deliveryForm.get(PHONE));
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
            Order.StatusType statusType = order.getStatusType();
            OrderService orderService = OrderServiceImpl.getInstance();
            Router router;
            if (statusType == Order.StatusType.PROCESSING) {
                if (orderService.confirmNewOrder(order.getId(), deliveryForm, Order.StatusType.PROCESSING)) {
                    session.removeAttribute(SessionAttribute.ORDER);
                    session.removeAttribute(SessionAttribute.ORDER_PRODUCTS);
                    session.setAttribute(SessionAttribute.CONFIRM_ORDER_SUCCESS, true);
                    router = new Router(PagePath.VIEW_CART, Router.Type.REDIRECT);
                } else {
                    session.setAttribute(SessionAttribute.DELIVERY_FORM, deliveryForm);
                    session.setAttribute(SessionAttribute.CONFIRM_ORDER_ERROR, true);
                    router = new Router(PagePath.ARRANGE_ORDER, Router.Type.REDIRECT);
                }
            } else {
                session.setAttribute(SessionAttribute.CONFIRM_ORDER_ERROR, true);
                order.setStatusType(Order.StatusType.NEW);
                router = new Router(PagePath.VIEW_CART, Router.Type.REDIRECT);
            }
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
