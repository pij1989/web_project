package com.pozharsky.dmitri.command.impl.pagecommand;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.Router;
import com.pozharsky.dmitri.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToChangePasswordPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CHANGE_PASSWORD);
        return new Router(PagePath.CHANGE_PASSWORD);
    }
}
