package com.pozharsky.dmitri.command.factory;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.CommandType;
import com.pozharsky.dmitri.command.RequestParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

public final class CommandFactory {

    private CommandFactory() {
    }

    public static Optional<Command> defineCommand(HttpServletRequest request) {
        String command = request.getParameter(RequestParameter.COMMAND);
        if (command != null && !command.isBlank()) {
            return Stream.of(CommandType.values())
                    .filter(e -> e.toString().equals(command.toUpperCase()))
                    .map(CommandType::getCommand).findAny();
        } else {
            return Optional.empty();
        }
    }
}
