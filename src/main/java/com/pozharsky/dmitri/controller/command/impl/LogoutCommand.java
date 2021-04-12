package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command for logout.
 *
 * @author Dmitri Pozharsky
 */
public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(PagePath.INDEX);
    }
}
