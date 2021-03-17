package com.pozharsky.dmitri.controller.listener;

import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class CustomSessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(CustomSessionListener.class);
    private static final int COUNT = 3;
    private static final String DEFAULT_LOCALE = "ru-BY";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.ROLE, User.RoleType.GUEST);
        session.setAttribute(SessionAttribute.LOCALE, Locale.forLanguageTag(DEFAULT_LOCALE));
        session.setAttribute(SessionAttribute.CURRENT_PAGE, new Router(PagePath.INDEX, Router.Type.REDIRECT));
        session.setAttribute(SessionAttribute.BLOCKING_COUNT, new AtomicInteger(COUNT));
        logger.info("Session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        logger.info("Session destroy");
    }
}
