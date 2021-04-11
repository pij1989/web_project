package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Interface that represents "Command" pattern. Used by a controller.
 *
 * @author Dmitri Pozharsky
 */
public interface Command {
    /**
     * Processes a request from controller and returns Router object, which contains page name and
     * type:redirect or forward.
     *
     * @param request HttpServletRequest object.
     * @return Router object with page name and it's type or null if page doesn't exist.
     * @throws CommandException if an exception has occurred while executing.
     */
    Router execute(HttpServletRequest request) throws CommandException;

    /**
     * Gets parameters from the request and return map where key is name of request's parameter and value is value of
     * request's parameter.
     *
     * @param request HttpServletRequest object.
     * @param requestParameters sequences of request's parameters names.
     * @return HashMap object.
     */
    default Map<String, String> requestParameterToMap(HttpServletRequest request, String... requestParameters) {
        HashMap<String, String> map = new HashMap<>();
        for (String parameter : requestParameters) {
            String parameterValue = request.getParameter(parameter);
            map.put(parameter, parameterValue);
        }
        return map;
    }
}
