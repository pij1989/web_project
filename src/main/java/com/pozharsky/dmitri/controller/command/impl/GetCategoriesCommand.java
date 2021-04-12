package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
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
 * Command for getting list of product's categories.
 *
 * @author Dmitri Pozharsky
 */
public class GetCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetCategoriesCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            List<Category> categories = categoryService.findAllCategory();
            HttpSession session = request.getSession();
            Router router = new Router(PagePath.CATEGORIES);
            session.setAttribute(SessionAttribute.CATEGORIES, categories);
            session.setAttribute(SessionAttribute.CURRENT_PAGE,router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
