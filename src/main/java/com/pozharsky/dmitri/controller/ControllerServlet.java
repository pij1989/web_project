package com.pozharsky.dmitri.controller;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.Router;
import com.pozharsky.dmitri.command.SessionAttribute;
import com.pozharsky.dmitri.command.factory.CommandFactory;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ControllerServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("We received request from URI: " + request.getRequestURI() + " content type: " + request.getContentType() + " method: " + request.getMethod());
        Optional<Command> optionalCommand = CommandFactory.defineCommand(request);
        if (optionalCommand.isPresent()) {
            Command command = optionalCommand.get();
            Router router = command.execute(request);
            if (router != null) {
                if (router.getType().equals(Router.Type.FORWARD)) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(router.getPagePath());
                    requestDispatcher.forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute(SessionAttribute.ROUTER, Router.Type.REDIRECT.toString());
                    response.sendRedirect(request.getContextPath() + router.getPagePath());
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        super.destroy();
    }
}
