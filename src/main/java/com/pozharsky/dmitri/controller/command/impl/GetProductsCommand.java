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

public class GetProductsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetProductsCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String page = request.getParameter(RequestParameter.PAGE);
            String perPage = request.getParameter(RequestParameter.PER_PAGE);
            logger.debug("Page: " + page + " perPage: " + perPage);
            ProductService productService = ProductServiceImpl.getInstance();
            int amountPage = productService.defineAmountProductPage(perPage);
            List<Product> products = productService.findProductsByPerPage(page, perPage);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.PRODUCTS, products);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.PRODUCTS);
            session.setAttribute(RequestAttribute.AMOUNT_PAGE, amountPage);
            session.setAttribute(RequestAttribute.PER_PAGE, perPage);
            return new Router(PagePath.PRODUCTS);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
