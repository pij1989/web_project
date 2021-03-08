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
import java.util.Optional;


public class EditCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditCategoryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String stringCategoryId = request.getParameter(RequestParameter.CATEGORY_ID);
        long categoryId;
        try {
            categoryId = Long.parseLong(stringCategoryId);
        } catch (NumberFormatException e) {
            logger.error("Incorrect category id");
            return new Router(PagePath.ERROR_404, Router.Type.REDIRECT);
        }
        try {
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            Optional<Category> optionalCategory = categoryService.findCategoryById(categoryId);
            HttpSession session = request.getSession();
            Router router;
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                ProductService productService = ProductServiceImpl.getInstance();
                List<Product> products = productService.findProductByCategory(categoryId);
                session.setAttribute(SessionAttribute.CATEGORY, category);
                session.setAttribute(SessionAttribute.PRODUCTS, products);
                router = new Router(PagePath.EDIT_CATEGORY);
            } else {
                request.setAttribute(RequestAttribute.CATEGORY_NOT_EXIST, true);
                router = new Router(PagePath.CATEGORIES);
            }
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
