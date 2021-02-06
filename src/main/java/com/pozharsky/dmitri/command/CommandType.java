package com.pozharsky.dmitri.command;

import com.pozharsky.dmitri.command.impl.*;
import com.pozharsky.dmitri.command.impl.pagecommand.ToUploadPageCommand;
import com.pozharsky.dmitri.command.impl.GetUsersCommand;
import com.pozharsky.dmitri.command.impl.pagecommand.ToLoginPageCommand;
import com.pozharsky.dmitri.command.impl.pagecommand.ToRegistrationPageCommand;

public enum CommandType {
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    LOGIN(new LoginCommand()),
    REGISTER(new RegistrationCommand()),
    ACTIVATE_REGISTRATION(new ActivateRegistrationCommand()),
    LOGOUT(new LogoutCommand()),
    GET_USERS_COMMAND(new GetUsersCommand()),
    TO_LOGIN_PAGE_COMMAND(new ToLoginPageCommand()),
    TO_REGISTER_PAGE_COMMAND(new ToRegistrationPageCommand()),
    TO_UPLOAD_PAGE_COMMAND(new ToUploadPageCommand());

    CommandType(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCommand() {
        return command;
    }
}
