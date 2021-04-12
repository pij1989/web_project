package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Command for searching products by product name.
 *
 * @author Dmitri Pozharsky
 */
public class SearchProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SearchProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String searchProduct = request.getParameter(RequestParameter.SEARCH_PRODUCT);
            HttpSession session = request.getSession();
            ProductService productService = ProductServiceImpl.getInstance();
            User.RoleType role = (User.RoleType) session.getAttribute(SessionAttribute.ROLE);
            Router router = null;
            List<Product> products = new ArrayList<>();
            if (role == User.RoleType.ADMIN) {
                products = productService.searchProduct(searchProduct);
                router = new Router(PagePath.RESULT_SEARCH_PRODUCTS_ADMIN);
            } else {
                if (role == User.RoleType.USER) {
                    products = productService.searchActiveProduct(searchProduct);
                    router = new Router(PagePath.RESULT_SEARCH_PRODUCTS_USER);
                }
            }
            request.setAttribute(RequestAttribute.SEARCH_PRODUCT, searchProduct);
            request.setAttribute(RequestAttribute.PRODUCTS, products);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
