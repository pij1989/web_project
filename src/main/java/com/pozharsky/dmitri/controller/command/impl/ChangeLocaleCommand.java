package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Command for changing locale.
 *
 * @author Dmitri Pozharsky
 */
public class ChangeLocaleCommand implements Command {
    private static final String RU_BY = "ru-BY";
    private static final String EN_US = "en-US";

    @Override
    public Router execute(HttpServletRequest request) {
        String languageTag = request.getParameter(RequestParameter.LANGUAGE);
        HttpSession session = request.getSession();
        Router router = (Router) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        if (languageTag != null) {
            Locale locale;
            switch (languageTag) {
                case EN_US:
                case RU_BY:
                    locale = Locale.forLanguageTag(languageTag);
                    break;
                default:
                    locale = Locale.forLanguageTag(RU_BY);
                    break;
            }
            session.setAttribute(SessionAttribute.LOCALE, locale);
        } else {
            session.setAttribute(SessionAttribute.LOCALE, Locale.forLanguageTag(RU_BY));
        }
        return router;
    }
}
