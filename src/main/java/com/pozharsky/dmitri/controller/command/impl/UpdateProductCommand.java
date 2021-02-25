package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpdateProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String productId = request.getParameter(RequestParameter.PRODUCT_ID);
            String productName = request.getParameter(RequestParameter.PRODUCT_NAME);
            String category = request.getParameter(RequestParameter.CATEGORY);
            String price = request.getParameter(RequestParameter.PRICE);
            String isActive = request.getParameter(RequestParameter.IS_ACTIVE_PRODUCT);
            String description = request.getParameter(RequestParameter.DESCRIPTION);
            String creatingTime = request.getParameter(RequestParameter.TIME_CREATE);
            HttpSession session = request.getSession();
            Part part;
            try {
                part = request.getPart(RequestParameter.IMAGE);
            } catch (IOException | ServletException e) {
                logger.fatal("Impossible receive part of form", e);
                throw new RuntimeException(e);
            }
            Map<String, String> productForm = new HashMap<>();
            productForm.put(RequestParameter.PRODUCT_NAME, productName);
            productForm.put(RequestParameter.CATEGORY, category);
            productForm.put(RequestParameter.PRICE, price);
            productForm.put(RequestParameter.IS_ACTIVE_PRODUCT, isActive);
            productForm.put(RequestParameter.DESCRIPTION, description);
            productForm.put(RequestParameter.TIME_CREATE, creatingTime);
            ProductService productService = ProductServiceImpl.getInstance();
            Optional<Product> optionalProduct = productService.updateProduct(productForm, productId, part);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                session.setAttribute(SessionAttribute.PRODUCT, product);
                session.setAttribute(SessionAttribute.UPDATE_PRODUCT_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.UPDATE_PRODUCT_ERROR, true);
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
