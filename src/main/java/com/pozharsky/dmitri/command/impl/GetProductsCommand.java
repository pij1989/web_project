package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.Router;
import com.pozharsky.dmitri.command.SessionAttribute;
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
    public Router execute(HttpServletRequest request) {
        try {
            ProductService productService = ProductServiceImpl.getInstance();
            List<Product> products = productService.findAllProducts();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.PRODUCTS, products);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.PRODUCTS);
            return new Router(PagePath.PRODUCTS);
        } catch (ServiceException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
