package com.pozharsky.dmitri.controller.command;

import com.pozharsky.dmitri.controller.command.impl.*;
import com.pozharsky.dmitri.controller.command.impl.pagecommand.*;

/**
 * Command's implementations enumeration.
 *
 * @author Dmitri Pozharsky
 */
public enum CommandType {

    /**
     * Represents LoginCommand implementation of Command interface.
     */
    LOGIN(new LoginCommand()),

    /**
     * Represents RegistrationCommand implementation of Command interface.
     */
    REGISTER(new RegistrationCommand()),

    /**
     * Represents ChangeLocaleCommand implementation of Command interface.
     */
    CHANGE_LOCALE(new ChangeLocaleCommand()),

    /**
     * Represents ActivateRegistrationCommand implementation of Command interface.
     */
    ACTIVATE_REGISTRATION(new ActivateRegistrationCommand()),

    /**
     * Represents UnblockUserCommand implementation of Command interface.
     */
    UNBLOCK_USER(new UnblockUserCommand()),

    /**
     * Represents ChangePasswordCommand implementation of Command interface.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    /**
     * Represents LogoutCommand implementation of Command interface.
     */
    LOGOUT(new LogoutCommand()),

    /**
     * Represents ChangeUserStatusCommand implementation of Command interface.
     */
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),

    /**
     * Represents CreateUserCommand implementation of Command interface.
     */
    CREATE_USER(new CreateUserCommand()),

    /**
     * Represents GetUsersCommand implementation of Command interface.
     */
    GET_USERS(new GetUsersCommand()),

    /**
     * Represents CreateProductCommand implementation of Command interface.
     */
    CREATE_PRODUCT(new CreateProductCommand()),

    /**
     * Represents EditProductCommand implementation of Command interface.
     */
    EDIT_PRODUCT(new EditProductCommand()),

    /**
     * Represents UpdateProductCommand implementation of Command interface.
     */
    UPDATE_PRODUCT(new UpdateProductCommand()),

    /**
     * Represents GetProductsCommand implementation of Command interface.
     */
    GET_PRODUCTS(new GetProductsCommand()),

    /**
     * Represents SearchProductCommand implementation of Command interface.
     */
    SEARCH_PRODUCT(new SearchProductCommand()),

    /**
     * Represents ViewProductCommand implementation of Command interface.
     */
    VIEW_PRODUCT(new ViewProductCommand()),

    /**
     * Represents FilterProductCommand implementation of Command interface.
     */
    FILTER_PRODUCT(new FilterProductCommand()),

    /**
     * Represents GetCategoriesCommand implementation of Command interface.
     */
    GET_CATEGORIES(new GetCategoriesCommand()),

    /**
     * Represents DeleteCategoryCommand implementation of Command interface.
     */
    DELETE_CATEGORY(new DeleteCategoryCommand()),

    /**
     * Represents CreateCategoryCommand implementation of Command interface.
     */
    CREATE_CATEGORY(new CreateCategoryCommand()),

    /**
     * Represents EditCategoryCommand implementation of Command interface.
     */
    EDIT_CATEGORY(new EditCategoryCommand()),

    /**
     * Represents UpdateCategoryCommand implementation of Command interface.
     */
    UPDATE_CATEGORY(new UpdateCategoryCommand()),

    /**
     * Represents AddReviewCommand implementation of Command interface.
     */
    ADD_REVIEW(new AddReviewCommand()),

    /**
     * Represents GetReviewsCommand implementation of Command interface.
     */
    GET_REVIEWS(new GetReviewsCommand()),

    /**
     * Represents DeleteReviewCommand implementation of Command interface.
     */
    DELETE_REVIEW(new DeleteReviewCommand()),

    /**
     * Represents DeleteProductFromOrderCommand implementation of Command interface.
     */
    DELETE_PRODUCT_FROM_ORDER(new DeleteProductFromOrderCommand()),

    /**
     * Represents ChangeAmountProductInOrderCommand implementation of Command interface.
     */
    CHANGE_AMOUNT_PRODUCT_IN_ORDER(new ChangeAmountProductInOrderCommand()),

    /**
     * Represents AddProductToOrderCommand implementation of Command interface.
     */
    ADD_PRODUCT_TO_ORDER(new AddProductToOrderCommand()),

    /**
     * Represents ViewCartCommand implementation of Command interface.
     */
    VIEW_CART(new ViewCartCommand()),

    /**
     * Represents ViewOrderCommand implementation of Command interface.
     */
    VIEW_ORDER(new ViewOrderCommand()),

    /**
     * Represents DeleteOrderCommand implementation of Command interface.
     */
    DELETE_ORDER(new DeleteOrderCommand()),

    /**
     * Represents ArrangeOrderCommand implementation of Command interface.
     */
    ARRANGE_ORDER(new ArrangeOrderCommand()),

    /**
     * Represents CancelArrangeOrderCommand implementation of Command interface.
     */
    CANCEL_ARRANGE_ORDER(new CancelArrangeOrderCommand()),

    /**
     * Represents ConfirmOrderCommand implementation of Command interface.
     */
    CONFIRM_ORDER(new ConfirmOrderCommand()),

    /**
     * Represents GetOrdersCommand implementation of Command interface.
     */
    GET_ORDERS(new GetOrdersCommand()),

    /**
     * Represents ChangeOrderStatusCommand implementation of Command interface.
     */
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),

    /**
     * Represents ToAdminPageCommand implementation of Command interface.
     */
    TO_ADMIN_PAGE_COMMAND(new ToAdminPageCommand()),

    /**
     * Represents ToMainPageCommand implementation of Command interface.
     */
    TO_MAIN_PAGE_COMMAND(new ToMainPageCommand()),

    /**
     * Represents ToCreatProductPageCommand implementation of Command interface.
     */
    TO_CREATE_PRODUCT_PAGE_COMMAND(new ToCreatProductPageCommand()),

    /**
     * Represents ToCreateUserPageCommand implementation of Command interface.
     */
    TO_CREATE_USER_PAGE_COMMAND(new ToCreateUserPageCommand()),

    /**
     * Represents ToLoginPageCommand implementation of Command interface.
     */
    TO_LOGIN_PAGE_COMMAND(new ToLoginPageCommand()),

    /**
     * Represents ToRegistrationPageCommand implementation of Command interface.
     */
    TO_REGISTER_PAGE_COMMAND(new ToRegistrationPageCommand()),

    /**
     * Represents ToChangePasswordPageCommand implementation of Command interface.
     */
    TO_CHANGE_PASSWORD_PAGE_COMMAND(new ToChangePasswordPageCommand());

    CommandType(Command command) {
        this.command = command;
    }

    private Command command;

    /**
     * Getter method of Command's implementation.
     * @return implementation of Command interface.
     */
    public Command getCommand() {
        return command;
    }
}
