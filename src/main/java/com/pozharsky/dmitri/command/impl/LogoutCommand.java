package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(PagePath.INDEX);
    }
}
