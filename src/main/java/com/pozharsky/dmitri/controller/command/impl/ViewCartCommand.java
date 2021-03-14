package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.OrderProduct;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.OrderService;
import com.pozharsky.dmitri.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewCartCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewCartCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);
            long userId = user.getId();
            OrderService orderService = OrderServiceImpl.getInstance();
            List<OrderProduct> orderProducts = orderService.findProductInNewOrder(userId);
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
