package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

/**
 * Command for creating product.
 *
 * @author Dmitri Pozharsky
 */
public class CreateProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            Map<String, String> productForm = requestParameterToMap(request, PRODUCT_NAME, CATEGORY, PRICE, AMOUNT,
                    IS_ACTIVE_PRODUCT, DESCRIPTION, TIME_CREATE);
            Part part;
            try {
                part = request.getPart(RequestParameter.IMAGE);
            } catch (IOException | ServletException e) {
                logger.fatal("Impossible receive part of form", e);
                throw new RuntimeException(e);
            }
            ProductService productService = ProductServiceImpl.getInstance();
            boolean result = productService.createProduct(productForm, part);
            HttpSession session = request.getSession();
            if (result) {
                session.setAttribute(SessionAttribute.CREATE_PRODUCT_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.PRODUCT_FORM, productForm);
                session.setAttribute(SessionAttribute.CREATE_PRODUCT_ERROR, true);
            }
            Router router = new Router(PagePath.CREATE_PRODUCT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
