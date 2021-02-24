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
import java.util.Optional;

public class EditProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String productId = request.getParameter(RequestParameter.PRODUCT_ID);
            HttpSession session = request.getSession();
            ProductService productService = ProductServiceImpl.getInstance();
            Optional<Product> optionalProduct = productService.findProductById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                logger.debug(product);
                session.setAttribute(SessionAttribute.PRODUCT, product);
            }
            Router router = new Router(PagePath.EDIT_PRODUCT, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
