package com.pozharsky.dmitri.controller;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.controller.command.factory.CommandFactory;
import com.pozharsky.dmitri.exception.CommandException;
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

/**
 * Controller class used to process all requests from users.
 *
 * @author Dmitri Pozharsky
 */
@WebServlet(urlPatterns = {"/controller"})
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
        try {
            logger.debug("We received request from URI: " + request.getRequestURI() + " content type: " + request.getContentType() + " method: " + request.getMethod());
            Optional<Command> optionalCommand = CommandFactory.defineCommand(request);
            if (optionalCommand.isPresent()) {
                Command command = optionalCommand.get();
                Router router = command.execute(request);
                if (router != null) {
                    if (router.getType() == Router.Type.FORWARD) {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(router.getPagePath());
                        requestDispatcher.forward(request, response);
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute(SessionAttribute.IS_REDIRECT, true);
                        response.sendRedirect(request.getContextPath() + router.getPagePath());
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (CommandException e) {
            logger.error("Redirect to the error page", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        super.destroy();
    }
}
