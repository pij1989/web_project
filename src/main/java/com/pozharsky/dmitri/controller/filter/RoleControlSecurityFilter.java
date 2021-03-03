package com.pozharsky.dmitri.controller.filter;

import com.pozharsky.dmitri.controller.command.CommandType;
import com.pozharsky.dmitri.controller.command.RequestParameter;
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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = {"/controller"}, initParams = @WebInitParam(name = "INDEX_PATH", value = "/index.jsp"))
public class RoleControlSecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(RoleControlSecurityFilter.class);
    private static final String INDEX_PATH = "INDEX_PATH";
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final Map<CommandType, List<RoleType>> getCommandMap = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, List<RoleType>> postCommandMap = new EnumMap<>(CommandType.class);
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter(INDEX_PATH);
        getCommandMap.put(CommandType.TO_CHANGE_PASSWORD_PAGE_COMMAND, List.of(RoleType.GUEST, RoleType.USER, RoleType.ADMIN));
        getCommandMap.put(CommandType.TO_CREATE_PRODUCT_PAGE_COMMAND, List.of(RoleType.ADMIN));
        getCommandMap.put(CommandType.TO_LOGIN_PAGE_COMMAND, List.of(RoleType.GUEST, RoleType.USER, RoleType.ADMIN));
        getCommandMap.put(CommandType.TO_REGISTER_PAGE_COMMAND, List.of(RoleType.GUEST, RoleType.USER, RoleType.ADMIN));
        getCommandMap.put(CommandType.ACTIVATE_REGISTRATION, List.of(RoleType.GUEST, RoleType.USER, RoleType.ADMIN));
        getCommandMap.put(CommandType.GET_PRODUCTS, List.of(RoleType.ADMIN, RoleType.USER));
        getCommandMap.put(CommandType.SEARCH_PRODUCT, List.of(RoleType.ADMIN));
        getCommandMap.put(CommandType.GET_USERS, List.of(RoleType.ADMIN));
        getCommandMap.put(CommandType.GET_CATEGORIES, List.of(RoleType.ADMIN));
        getCommandMap.put(CommandType.LOGOUT, List.of(RoleType.ADMIN, RoleType.USER));
        postCommandMap.put(CommandType.EDIT_CATEGORY, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.CHANGE_LOCALE, List.of(RoleType.ADMIN, RoleType.USER, RoleType.GUEST));
        postCommandMap.put(CommandType.CHANGE_PASSWORD, List.of(RoleType.ADMIN, RoleType.USER, RoleType.GUEST));
        postCommandMap.put(CommandType.CHANGE_USER_STATUS, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.CREATE_PRODUCT, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.DELETE_CATEGORY, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.LOGIN, List.of(RoleType.ADMIN, RoleType.USER, RoleType.GUEST));
        postCommandMap.put(CommandType.REGISTER, List.of(RoleType.ADMIN, RoleType.USER, RoleType.GUEST));
        postCommandMap.put(CommandType.CREATE_CATEGORY, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.UPDATE_CATEGORY, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.EDIT_PRODUCT, List.of(RoleType.ADMIN));
        postCommandMap.put(CommandType.UPDATE_PRODUCT, List.of(RoleType.ADMIN));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String method = httpServletRequest.getMethod();
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(RequestParameter.COMMAND);
        RoleType role = (RoleType) session.getAttribute(SessionAttribute.ROLE);
        if (command != null) {
            CommandType commandType;
            try {
                commandType = CommandType.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("This command '" + command + "'doesn't exist", e);
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (!hasPermission(commandType, role, method)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        indexPath = null;
    }

    private boolean hasPermission(CommandType commandType, RoleType roleType, String method) {
        boolean hasPermission = false;
        switch (method) {
            case GET_METHOD: {
                hasPermission = hasPermission(commandType, roleType, getCommandMap);
                break;
            }
            case POST_METHOD: {
                hasPermission = hasPermission(commandType, roleType, postCommandMap);
                break;
            }
        }
        return hasPermission;
    }

    private boolean hasPermission(CommandType commandType, RoleType roleType, Map<CommandType, List<RoleType>> commandMap) {
        boolean hasPermission = false;
        if (commandMap.get(commandType) != null) {
            List<RoleType> roles = commandMap.get(commandType);
            if (roles.contains(roleType)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }
}
