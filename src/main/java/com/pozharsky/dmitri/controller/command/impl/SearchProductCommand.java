package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SearchProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SearchProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String searchProduct = request.getParameter(RequestParameter.SEARCH_PRODUCT);
            HttpSession session = request.getSession();
            ProductService productService = ProductServiceImpl.getInstance();
            List<Product> products = productService.searchProduct(searchProduct);
            session.setAttribute(SessionAttribute.PRODUCTS, products);
            Router router = new Router(PagePath.RESULT_SEARCH_PRODUCTS, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}