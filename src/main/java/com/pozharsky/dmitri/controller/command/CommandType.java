package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.controller.command.impl.*;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.*;

public enum CommandType {
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    LOGIN(new LoginCommand()),
    REGISTER(new RegistrationCommand()),
    ACTIVATE_REGISTRATION(new ActivateRegistrationCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    CREATE_PRODUCT(new CreateProductCommand()),
    EDIT_PRODUCT(new EditProductCommand()),
    UPDATE_PRODUCT(new UpdateProductCommand()),
    GET_USERS(new GetUsersCommand()),
    GET_PRODUCTS(new GetProductsCommand()),
    SEARCH_PRODUCT(new SearchProductCommand()),
    VIEW_PRODUCT(new ViewProductCommand()),
    GET_CATEGORIES(new GetCategoriesCommand()),
    DELETE_CATEGORY(new DeleteCategoryCommand()),
    CREATE_CATEGORY(new CreateCategoryCommand()),
    CREATE_USER(new CreateUserCommand()),
    EDIT_CATEGORY(new EditCategoryCommand()),
    UPDATE_CATEGORY(new UpdateCategoryCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    TO_ADMIN_PAGE_COMMAND(new ToAdminPageCommand()),
    TO_MAIN_PAGE_COMMAND(new ToMainPageCommand()),
    TO_CREATE_PRODUCT_PAGE_COMMAND(new ToCreatProductPageCommand()),
    TO_CREATE_USER_PAGE_COMMAND(new ToCreateUserPageCommand()),
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
