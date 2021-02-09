package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String value = request.getParameter(RequestParameter.LANGUAGE);
        HttpSession session = request.getSession();
        String pagePath = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        LanguageType languageType = LanguageType.valueOf(value.toUpperCase());
        switch (languageType) {
            case RU: {
                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.RU.getLanguage());
                break;
            }
            case EN: {
                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.EN.getLanguage());
                break;
            }
            default: {
                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.RU.getLanguage());
            }
        }
        return new Router(pagePath, Router.Type.REDIRECT);
    }
}
