package com.pozharsky.dmitri.controller.command.impl.pagecommand;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for forwarding to main page.
 *
 * @author Dmitri Pozharsky
 */
public class ToMainPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.MAIN);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
        return router;
    }
}
