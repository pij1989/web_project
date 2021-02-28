package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.service.CategoryService;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.CategoryServiceImpl;
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
            String categoryId = request.getParameter(RequestParameter.CATEGORY_ID);
            ProductService productService = ProductServiceImpl.getInstance();
            HttpSession session = request.getSession();
            logger.debug("Page: " + page + " perPage: " + perPage);
            List<Product> products;
            int amountProduct;
            if (categoryId == null || categoryId.isBlank()) {
                amountProduct = productService.defineAmountProduct();
                products = productService.findProductsByPerPage(page, perPage);
            } else {
                amountProduct = productService.defineAmountProductByCategory(categoryId);
                products = productService.findProductsByCategoryAndPerPage(categoryId, page, perPage);
            }
            if (session.getAttribute(SessionAttribute.CATEGORIES) == null) {
                CategoryService categoryService = CategoryServiceImpl.getInstance();
                List<Category> categories = categoryService.findAllCategory();
                session.setAttribute(SessionAttribute.CATEGORIES, categories);
            }
            session.setAttribute(SessionAttribute.PRODUCTS, products);
            session.setAttribute(SessionAttribute.AMOUNT_PRODUCT, amountProduct);
            session.setAttribute(SessionAttribute.SELECTED_CATEGORY, categoryId);
            Router router = new Router(PagePath.PRODUCTS);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
