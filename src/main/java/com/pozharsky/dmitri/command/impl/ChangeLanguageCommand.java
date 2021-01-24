package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String RU = "ru";
    private static final String EN = "en";
    private static final String LANGUAGE = "language";
    private static final String LANGUAGE_RU = "ru";
    private static final String LANGUAGE_EN = "en";

    @Override
    public Router execute(HttpServletRequest request) {
        String value = request.getParameter(RequestParameter.LANGUAGE);
        HttpSession session = request.getSession();
        String pagePath = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        switch (value) {
            case RU: {
                session.setAttribute(LANGUAGE, LANGUAGE_RU);
                break;
            }
            case EN: {
                session.setAttribute(LANGUAGE, LANGUAGE_EN);
                break;
            }
            default: {
                session.setAttribute(LANGUAGE, LANGUAGE_RU);
            }
        }
        return new Router(pagePath, Router.Type.REDIRECT);
    }
}
