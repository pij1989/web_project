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

/**
 * Command for updating product's category.
 *
 * @author Dmitri Pozharsky
 */
public class UpdateCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateCategoryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String categoryId = request.getParameter(RequestParameter.CATEGORY_ID);
            String categoryName = request.getParameter(RequestParameter.CATEGORY_NAME);
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            HttpSession session = request.getSession();
            if (categoryService.updateCategory(categoryId, categoryName).isPresent()) {
                Category category = (Category) session.getAttribute(SessionAttribute.CATEGORY);
                category.setName(categoryName);
                session.setAttribute(SessionAttribute.UPDATE_CATEGORY_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.UPDATE_CATEGORY_ERROR, true);
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
