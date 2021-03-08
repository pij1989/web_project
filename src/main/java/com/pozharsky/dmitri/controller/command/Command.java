package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;

    default Map<String, String> requestParameterToMap(HttpServletRequest request, String... requestParameters) {
        HashMap<String, String> map = new HashMap<>();
        for (String parameter : requestParameters) {
            String parameterValue = request.getParameter(parameter);
            map.put(parameter, parameterValue);
        }
        return map;
    }
}
