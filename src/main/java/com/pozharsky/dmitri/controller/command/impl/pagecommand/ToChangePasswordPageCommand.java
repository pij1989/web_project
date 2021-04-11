package com.pozharsky.dmitri.controller.command.impl.pagecommand;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for forwarding to change password page.
 *
 * @author Dmitri Pozharsky
 */
public class ToChangePasswordPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.CHANGE_PASSWORD);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
        return router;
    }
}
