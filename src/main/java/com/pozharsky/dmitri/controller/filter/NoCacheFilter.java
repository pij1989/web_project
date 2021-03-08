package com.pozharsky.dmitri.controller.filter;

import com.pozharsky.dmitri.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/*"}, initParams = @WebInitParam(name = "Cache-Control", value = "no-cache, no-store, must-revalidate"))
public class NoCacheFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(NoCacheFilter.class);
    private static final String CACHE_CONTROL = "Cache-Control";
    private String value;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        value = filterConfig.getInitParameter(CACHE_CONTROL);
        /*logger.debug("Do init...");*/
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*logger.debug("Do filter...");
        logger.debug(((HttpServletRequest)request).getRequestURI());*/
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(CACHE_CONTROL, value);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        value = null;
    }
}
