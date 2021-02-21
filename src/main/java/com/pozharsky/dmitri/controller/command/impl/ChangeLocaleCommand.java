package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String languageTag = request.getParameter(RequestParameter.LANGUAGE);
        HttpSession session = request.getSession();
        Router router = (Router) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        if (languageTag != null) {
            Locale locale = Locale.forLanguageTag(languageTag);
            session.setAttribute(SessionAttribute.LOCALE, locale);
        } else {
            session.setAttribute(SessionAttribute.LOCALE, Locale.getDefault());
        }
        return router;
    }
}
