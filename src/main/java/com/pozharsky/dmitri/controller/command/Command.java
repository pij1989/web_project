package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
