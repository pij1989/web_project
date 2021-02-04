package com.pozharsky.dmitri.command.impl.pagecommand;

import com.pozharsky.dmitri.command.Command;
import com.pozharsky.dmitri.command.PagePath;
import com.pozharsky.dmitri.command.Router;

import javax.servlet.http.HttpServletRequest;

public class ToUploadPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
       return new Router(PagePath.UPLOAD);
    }
}
