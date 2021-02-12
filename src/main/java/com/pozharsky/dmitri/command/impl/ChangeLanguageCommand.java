package com.pozharsky.dmitri.command.impl;

import com.pozharsky.dmitri.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String value = request.getParameter(RequestParameter.LANGUAGE);
        HttpSession session = request.getSession();
        String pagePath = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        Language language = Language.valueOf(value.toUpperCase());
        switch (language) {
            case RU: {
//                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.RU.getLanguage());
                session.setAttribute(SessionAttribute.LOCALE, new Locale(Language.RU.getLanguage(), Country.RU.getCountry()));
                break;
            }
            case EN: {
//                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.EN.getLanguage());
                session.setAttribute(SessionAttribute.LOCALE, new Locale(Language.EN.getLanguage(), Country.US.getCountry()));
                break;
            }
            default: {
//                session.setAttribute(SessionAttribute.LANGUAGE, LanguageType.RU.getLanguage());
                session.setAttribute(SessionAttribute.LOCALE, new Locale(Language.RU.getLanguage(), Country.RU.getCountry()));
            }
        }
        return new Router(pagePath, Router.Type.REDIRECT);
    }
}
