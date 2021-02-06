package com.pozharsky.dmitri.controller;

import com.pozharsky.dmitri.command.RequestParameter;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.service.UserService;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/changestatus")
public class ChangeStatusServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ChangeStatusServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter(RequestParameter.USER_ID);
            String status = request.getParameter(RequestParameter.STATUS);
            logger.debug("User id: " + userId + " Status: " + status);
            UserService userService = UserServiceImpl.getInstance();
            boolean result = userService.changeUserStatus(Long.parseLong(userId), StatusType.valueOf(status));
            if (result) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (ServiceException e) {
            logger.error(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
