package com.pozharsky.dmitri.controller.filter;

import com.pozharsky.dmitri.controller.command.CommandType;
import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.User;
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
    private static final Map<CommandType, List<User.RoleType>> getCommandMap = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, List<User.RoleType>> postCommandMap = new EnumMap<>(CommandType.class);
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter(INDEX_PATH);
        getCommandMap.put(CommandType.TO_CHANGE_PASSWORD_PAGE_COMMAND, List.of(User.RoleType.GUEST));
        getCommandMap.put(CommandType.TO_CREATE_PRODUCT_PAGE_COMMAND, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.TO_LOGIN_PAGE_COMMAND, List.of(User.RoleType.GUEST));
        getCommandMap.put(CommandType.TO_REGISTER_PAGE_COMMAND, List.of(User.RoleType.GUEST, User.RoleType.USER));
        getCommandMap.put(CommandType.TO_ADMIN_PAGE_COMMAND, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.TO_MAIN_PAGE_COMMAND, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.TO_CREATE_USER_PAGE_COMMAND, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.ACTIVATE_REGISTRATION, List.of(User.RoleType.GUEST, User.RoleType.USER, User.RoleType.ADMIN));
        getCommandMap.put(CommandType.GET_PRODUCTS, List.of(User.RoleType.ADMIN, User.RoleType.USER));
        getCommandMap.put(CommandType.SEARCH_PRODUCT, List.of(User.RoleType.ADMIN, User.RoleType.USER));
        getCommandMap.put(CommandType.VIEW_PRODUCT, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.FILTER_PRODUCT, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.VIEW_ORDER, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.ARRANGE_ORDER, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.CANCEL_ARRANGE_ORDER, List.of(User.RoleType.USER));
        postCommandMap.put(CommandType.CONFIRM_ORDER, List.of(User.RoleType.USER));
        getCommandMap.put(CommandType.GET_USERS, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.GET_CATEGORIES, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.LOGOUT, List.of(User.RoleType.ADMIN, User.RoleType.USER));
        getCommandMap.put(CommandType.EDIT_CATEGORY, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.EDIT_PRODUCT, List.of(User.RoleType.ADMIN));
        getCommandMap.put(CommandType.CHANGE_LOCALE, List.of(User.RoleType.ADMIN, User.RoleType.USER, User.RoleType.GUEST));
        postCommandMap.put(CommandType.CHANGE_PASSWORD, List.of(User.RoleType.ADMIN, User.RoleType.USER, User.RoleType.GUEST));
        postCommandMap.put(CommandType.CHANGE_USER_STATUS, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.CREATE_PRODUCT, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.CREATE_USER, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.DELETE_CATEGORY, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.LOGIN, List.of(User.RoleType.ADMIN, User.RoleType.USER, User.RoleType.GUEST));
        postCommandMap.put(CommandType.REGISTER, List.of(User.RoleType.ADMIN, User.RoleType.USER, User.RoleType.GUEST));
        postCommandMap.put(CommandType.CREATE_CATEGORY, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.UPDATE_CATEGORY, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.UPDATE_PRODUCT, List.of(User.RoleType.ADMIN));
        postCommandMap.put(CommandType.ADD_REVIEW, List.of(User.RoleType.USER));
        postCommandMap.put(CommandType.ADD_PRODUCT_TO_ORDER, List.of(User.RoleType.USER));
        postCommandMap.put(CommandType.DELETE_PRODUCT_FROM_ORDER, List.of(User.RoleType.USER));
        postCommandMap.put(CommandType.CHANGE_AMOUNT_PRODUCT_IN_ORDER, List.of(User.RoleType.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String method = httpServletRequest.getMethod();
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(RequestParameter.COMMAND);
        User.RoleType role = (User.RoleType) session.getAttribute(SessionAttribute.ROLE);
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
                logger.debug("No permission");
                if (role == User.RoleType.USER || role == User.RoleType.ADMIN) {
                    session.invalidate();
                }
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

    private boolean hasPermission(CommandType commandType, User.RoleType roleType, String method) {
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

    private boolean hasPermission(CommandType commandType, User.RoleType roleType, Map<CommandType, List<User.RoleType>> commandMap) {
        boolean hasPermission = false;
        if (commandMap.get(commandType) != null) {
            List<User.RoleType> roles = commandMap.get(commandType);
            if (roles.contains(roleType)) {
                hasPermission = true;
            }
        }
        return hasPermission;
    }
}
