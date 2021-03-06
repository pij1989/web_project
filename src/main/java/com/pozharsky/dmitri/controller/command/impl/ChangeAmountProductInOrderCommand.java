package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Order;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Command for changing amount of product in user's order.
 *
 * @author Dmitri Pozharsky
 */
public class ChangeAmountProductInOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeAmountProductInOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String orderProductId = request.getParameter(RequestParameter.ORDER_PRODUCT_ID);
            String amountProduct = request.getParameter(RequestParameter.AMOUNT_PRODUCT);
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<OrderProduct> optionalOrderProduct = orderService.changeAmountProductInOrder(orderProductId, amountProduct, order);
            if (optionalOrderProduct.isPresent()) {
                List<OrderProduct> orderProducts = orderService.findProductInOrder(order.getId());
                session.setAttribute(SessionAttribute.ORDER_PRODUCTS, orderProducts);
            } else {
                session.setAttribute(SessionAttribute.CHANGE_AMOUNT_PRODUCT_ERROR, true);
            }
            Router router = new Router(PagePath.VIEW_CART, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
