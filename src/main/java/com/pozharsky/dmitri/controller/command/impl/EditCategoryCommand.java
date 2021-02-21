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
        try {
            String categoryId = request.getParameter(RequestParameter.CATEGORY_ID);
            logger.debug("CategoryId: " + categoryId);
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            Optional<Category> optionalCategory = categoryService.findCategoryById(categoryId);
            HttpSession session = request.getSession();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                ProductService productService = ProductServiceImpl.getInstance();
                List<Product> products = productService.findProductByCategory(categoryId);
                session.setAttribute(SessionAttribute.CATEGORY, category);
                session.setAttribute(SessionAttribute.PRODUCTS, products);
            }
            Router router = new Router(PagePath.EDIT_CATEGORY, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
