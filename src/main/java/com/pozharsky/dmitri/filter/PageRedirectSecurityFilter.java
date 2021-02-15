package com.pozharsky.dmitri.filter;

import com.pozharsky.dmitri.command.SessionAttribute;
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
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("Start filter...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String sessionAttribute = (String) session.getAttribute(SessionAttribute.ROUTER);
        if (sessionAttribute != null) {
            session.removeAttribute(SessionAttribute.ROUTER);
            logger.debug("Do chain...");
            chain.doFilter(request, response);
        } else {
            logger.debug("Send redirect...");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
        }
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
