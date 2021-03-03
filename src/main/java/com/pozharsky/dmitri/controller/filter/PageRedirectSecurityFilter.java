package com.pozharsky.dmitri.controller.filter;

import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"}, initParams = @WebInitParam(name = "INDEX_PATH", value = "/index.jsp"))
public class PageRedirectSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PageRedirectSecurityFilter.class);
    private static final String INDEX_PATH = "INDEX_PATH";
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter(INDEX_PATH);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        Boolean isRedirect = (Boolean) session.getAttribute(SessionAttribute.IS_REDIRECT);
        if (isRedirect != null && isRedirect) {
            logger.debug("Redirect: " + ((HttpServletRequest) request).getRequestURI());
            session.setAttribute(SessionAttribute.IS_REDIRECT, false);
            chain.doFilter(request, response);
        } else {
            Router router = (Router) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            RoleType role = (RoleType) session.getAttribute(SessionAttribute.ROLE);
            if (role == null || role.equals(RoleType.GUEST)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
                logger.debug("Redirect to index: " + role);
                return;
            }
            logger.debug("Redirect: " + role);
            session.setAttribute(SessionAttribute.IS_REDIRECT, true);
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + router.getPagePath());
        }
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
