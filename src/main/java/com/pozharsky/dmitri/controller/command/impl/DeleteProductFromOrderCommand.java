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

public class DeleteProductFromOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteProductFromOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String orderProductId = request.getParameter(RequestParameter.ORDER_PRODUCT_ID);
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(SessionAttribute.ORDER);
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<OrderProduct> optionalOrderProduct = orderService.deleteProductFromOrder(orderProductId, order);
            if (optionalOrderProduct.isPresent()) {
                OrderProduct orderProduct = optionalOrderProduct.get();
                @SuppressWarnings("unchecked")
                List<OrderProduct> orderProducts = (List<OrderProduct>) session.getAttribute(SessionAttribute.ORDER_PRODUCTS);
                orderProducts.remove(orderProduct);
                if (orderProducts.isEmpty() && order.getStatusType() == Order.StatusType.NEW) {
                    if (orderService.deleteOrder(order.getId())) {
                        session.removeAttribute(SessionAttribute.ORDER);
                    }
                }
                session.setAttribute(SessionAttribute.DELETE_PRODUCT_FROM_ORDER_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.DELETE_PRODUCT_FROM_ORDER_ERROR, true);
            }
            Router router = new Router(PagePath.VIEW_ORDER, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
