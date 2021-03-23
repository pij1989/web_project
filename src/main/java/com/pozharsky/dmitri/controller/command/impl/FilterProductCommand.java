package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

public class FilterProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FilterProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String stringCategoryId = request.getParameter(CATEGORY_ID);
        logger.debug("Category id: " + stringCategoryId);
        long categoryId;
        try {
            categoryId = Long.parseLong(stringCategoryId);
        } catch (NumberFormatException e) {
            logger.error("Incorrect category id", e);
            return new Router(PagePath.ERROR_404, Router.Type.REDIRECT);
        }
        try {
            Map<String, String> filterForm = requestParameterToMap(request, PRICE_FROM, PRICE_TO, IN_STOCK);
            String sort = request.getParameter(RequestAttribute.SORT_TYPE);
            HttpSession session = request.getSession();
            logger.debug("Price from: " + filterForm.get(PRICE_FROM) + " Price to: " + filterForm.get(PRICE_TO) + " In stock: " + filterForm.get(IN_STOCK));
            ProductService productService = ProductServiceImpl.getInstance();
            List<Product> products = productService.filterActiveProduct(categoryId, filterForm,sort);
            request.setAttribute(RequestAttribute.PRODUCTS, products);
            request.setAttribute(RequestAttribute.FILTER_PRODUCT_FORM, filterForm);
            request.setAttribute(RequestAttribute.WITH_FILTER, true);
            Router router = new Router(PagePath.PRODUCTS_USER);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (Exception e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
