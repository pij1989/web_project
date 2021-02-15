package com.pozharsky.dmitri.controller.listener;

import com.pozharsky.dmitri.controller.command.PagePath;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.RoleType;
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
    private static final int COUNT = 3;
    private static final Logger logger = LogManager.getLogger(CustomSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.ROLE, RoleType.GUEST.toString());
        session.setAttribute(SessionAttribute.LOCALE, Locale.getDefault());
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.INDEX);
        session.setAttribute(SessionAttribute.BLOCKING_COUNT, new AtomicInteger(COUNT));
        logger.info("Session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        logger.info("Session destroy");
    }
}
