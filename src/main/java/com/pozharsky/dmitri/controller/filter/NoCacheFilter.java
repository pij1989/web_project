package com.pozharsky.dmitri.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web filter used to exclude cache.
 *
 * @author Dmitri Pozharsky
 */
@WebFilter(value = {"/*"},
        initParams = {@WebInitParam(name = "Cache-Control", value = "no-cache, no-store, must-revalidate")})
public class NoCacheFilter implements Filter {
    private static final String CACHE_CONTROL = "Cache-Control";
    private String value;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        value = filterConfig.getInitParameter(CACHE_CONTROL);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(CACHE_CONTROL, value);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        value = null;
    }
}
