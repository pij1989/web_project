package com.pozharsky.dmitri.controller.command.impl.pagecommand;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToChangePasswordPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = new Router(PagePath.CHANGE_PASSWORD);
//        session.setAttribute(SessionAttribute.CURRENT_PAGE, new Router(PagePath.CHANGE_PASSWORD, Router.Type.REDIRECT));
        session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
        return router;
    }
}
