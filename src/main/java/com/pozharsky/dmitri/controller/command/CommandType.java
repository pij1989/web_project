package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.controller.command.impl.*;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.ToChangePasswordPageCommand;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.ToCreatProductPageCommand;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.ToLoginPageCommand;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.ToRegistrationPageCommand;

public enum CommandType {
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    LOGIN(new LoginCommand()),
    REGISTER(new RegistrationCommand()),
    ACTIVATE_REGISTRATION(new ActivateRegistrationCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    CREATE_PRODUCT(new CreateProductCommand()),
    GET_USERS(new GetUsersCommand()),
    GET_PRODUCTS(new GetProductsCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    TO_CREATE_PRODUCT_PAGE_COMMAND(new ToCreatProductPageCommand()),
    TO_LOGIN_PAGE_COMMAND(new ToLoginPageCommand()),
    TO_REGISTER_PAGE_COMMAND(new ToRegistrationPageCommand()),
    TO_CHANGE_PASSWORD_PAGE_COMMAND(new ToChangePasswordPageCommand());

    CommandType(Command command) {
        this.command = command;
    }

    private Command command;

    public Command getCommand() {
        return command;
    }
}
