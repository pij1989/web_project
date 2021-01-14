package com.pozharsky.dmitri.listener;

import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class UserSessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(UserSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.ROLE, RoleType.GUEST.toString());
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.INDEX);
        logger.debug("Session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.debug("Session destroy");
    }
}
