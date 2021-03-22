package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.CategoryService;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.CategoryServiceImpl;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class GetProductsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetProductsCommand.class);
    private static final long ZERO = 0L;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String stringCategoryId = request.getParameter(RequestParameter.CATEGORY_ID);
        logger.debug("Category id: " + stringCategoryId);
        long categoryId;
        try {
            categoryId = stringCategoryId != null ? Long.parseLong(stringCategoryId) : ZERO;
        } catch (NumberFormatException e) {
            logger.error("Incorrect category id", e);
            return new Router(PagePath.ERROR_404, Router.Type.REDIRECT);
        }
        try {
            String page = request.getParameter(RequestParameter.PAGE);
            String perPage = request.getParameter(RequestParameter.PER_PAGE);
            String sort = request.getParameter(RequestParameter.SORT);
            ProductService productService = ProductServiceImpl.getInstance();
            HttpSession session = request.getSession();
            User.RoleType roleType = (User.RoleType) session.getAttribute(SessionAttribute.ROLE);
            logger.debug("Page: " + page + " perPage: " + perPage + " categoryId: " + categoryId + " Sort: " + sort);
            int amountProduct = 0;
            List<Product> products = new ArrayList<>();
            Router router = null;
            if (roleType == User.RoleType.ADMIN) {
                if (categoryId == ZERO) {
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
                router = new Router(PagePath.PRODUCTS_ADMIN);
            } else {
                if (roleType == User.RoleType.USER) {
                    amountProduct = productService.defineAmountActiveProductByCategory(categoryId);
                    products = productService.findActiveProductsByCategoryAndPerPage(categoryId, page, perPage, sort);
                    request.setAttribute(RequestAttribute.SORT_TYPE, sort);
                    router = new Router(PagePath.PRODUCTS_USER);
                }
            }
            request.setAttribute(RequestAttribute.PRODUCTS, products);
            request.setAttribute(RequestAttribute.AMOUNT_PRODUCT, amountProduct);
            session.setAttribute(SessionAttribute.SELECTED_CATEGORY, categoryId);
            //TODO: change session to request
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
