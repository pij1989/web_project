package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Category;
import com.pozharsky.dmitri.model.service.CategoryService;
import com.pozharsky.dmitri.model.service.impl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for creating category.
 *
 * @author Dmitri Pozharsky
 */
public class CreateCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateCategoryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String categoryName = request.getParameter(RequestParameter.CATEGORY_NAME);
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            HttpSession session = request.getSession();
            Category category = new Category(categoryName);
            if (categoryService.createCategory(category)) {
                @SuppressWarnings("unchecked")
                List<Category> categories = (List<Category>) session.getAttribute(SessionAttribute.CATEGORIES);
                categories.add(category);
                session.setAttribute(SessionAttribute.CREATE_CATEGORY_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.CREATE_CATEGORY_ERROR, true);
            }
            Router router = new Router(PagePath.CATEGORIES, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
