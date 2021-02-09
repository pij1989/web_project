package com.pozharsky.dmitri.command;

import com.pozharsky.dmitri.command.impl.*;
import com.pozharsky.dmitri.command.impl.pagecommand.ToCreatProductPage;
import com.pozharsky.dmitri.command.impl.pagecommand.ToLoginPageCommand;
import com.pozharsky.dmitri.command.impl.pagecommand.ToRegistrationPageCommand;
import com.pozharsky.dmitri.command.impl.pagecommand.ToUploadPageCommand;

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
    TO_CREATE_PRODUCT_PAGE(new ToCreatProductPage()),
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
