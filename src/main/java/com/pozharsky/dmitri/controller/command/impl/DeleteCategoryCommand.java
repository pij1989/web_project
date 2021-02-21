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

public class DeleteCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCategoryCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String categoryId = request.getParameter(RequestParameter.CATEGORY_ID);
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            HttpSession session = request.getSession();
            if (categoryService.deleteCategoryAndProducts(categoryId)) {
                List<Category> categories = categoryService.findAllCategory();
                session.setAttribute(SessionAttribute.CATEGORIES, categories);
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
