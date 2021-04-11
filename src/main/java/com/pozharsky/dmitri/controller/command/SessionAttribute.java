package com.pozharsky.dmitri.controller.command;

/**
 * Class represents constant names of session attributes.
 *
 * @author Dmitri Pozharsky
 */
public class SessionAttribute {

    /**
     * Represents user's role.
     */
    public static final String ROLE = "role";

    /**
     * Represents user's username on appropriate page.
     */
    public static final String USERNAME = "username";

    /**
     * Represents current page for localization.
     */
    public static final String CURRENT_PAGE = "currentPage";

    /**
     * Represents user on appropriate page.
     */
    public static final String USERS = "users";

    /**
     * Represents message about success change status.
     */
    public static final String CHANGE_STATUS_SUCCESS = "changeStatusSuccess";

    /**
     * Represents message about error change status.
     */
    public static final String CHANGE_STATUS_ERROR = "changeStatusError";

    /**
     * Represents flag for checking it's redirect or not.
     */
    public static final String IS_REDIRECT = "isRedirect";

    /**
     * Represents locale for localization.
     */
    public static final String LOCALE = "locale";

    /**
     * Represents user's email.
     */
    public static final String EMAIL = "email";

    /**
     * Represents count for blocking user, if password was entered incorrect repeatedly.
     */
    public static final String BLOCKING_COUNT = "blockingCount";

    /**
     * Represents errors on appropriate page.
     */
    public static final String ERRORS = "errors";

    /**
     * Represents user's errors on appropriate page.
     */
    public static final String ERROR_USER = "errorUser";

    /**
     * Represents errors of activating registration on appropriate page.
     */
    public static final String ERROR_ACTIVATE_REGISTRATION = "errorActivateRegistration";

    /**
     * Represents registration form on appropriate page.
     */
    public static final String REGISTRATION_FORM = "registrationForm";

    /**
     * Represents message of entering mismatched password on appropriate page.
     */
    public static final String MISMATCHED_PASSWORD = "mismatchedPassword";

    /**
     * Represents message on appropriate page that user was blocked.
     */
    public static final String BLOCKED_USER = "blockedUser";

    /**
     * Represents message about success change password.
     */
    public static final String CHANGE_PASSWORD_SUCCESS = "changePasswordSuccess";

    /**
     * Represents message about error change password.
     */
    public static final String CHANGE_PASSWORD_ERROR = "changePasswordError";

    /**
     * Represents total amount of products.
     */
    public static final String AMOUNT_PRODUCT = "amountProduct";

    /**
     * Represents list of products on appropriate page.
     */
    public static final String PRODUCTS = "products";

    /**
     * Represents product form on appropriate page.
     */
    public static final String PRODUCT_FORM = "productForm";

    /**
     * Represents list of categories on appropriate page.
     */
    public static final String CATEGORIES = "categories";

    /**
     * Represents message about error of creating product.
     */
    public static final String CREATE_PRODUCT_ERROR = "createProductError";

    /**
     * Represents message about success creating product.
     */
    public static final String CREATE_PRODUCT_SUCCESS = "createProductSuccess";

    /**
     * Represents message about success creating category.
     */
    public static final String CREATE_CATEGORY_SUCCESS = "createCategorySuccess";

    /**
     * Represents message about error creating category.
     */
    public static final String CREATE_CATEGORY_ERROR = "createCategoryError";

    /**
     * Represents category on appropriate page.
     */
    public static final String CATEGORY = "category";

    /**
     * Represents message about success updating category.
     */
    public static final String UPDATE_CATEGORY_SUCCESS = "updateCategorySuccess";

    /**
     * Represents message about error updating category.
     */
    public static final String UPDATE_CATEGORY_ERROR = "updateCategoryError";

    /**
     * Represents product on appropriate page.
     */
    public static final String PRODUCT = "product";

    /**
     * Represents message about success updating product.
     */
    public static final String UPDATE_PRODUCT_SUCCESS = "updateProductSuccess";

    /**
     * Represents message about error updating product.
     */
    public static final String UPDATE_PRODUCT_ERROR = "updateProductError";

    /**
     * Represents selected category on appropriate page.
     */
    public static final String SELECTED_CATEGORY = "selectedCategory";

    /**
     * Represents user on appropriate page.
     */
    public static final String USER = "user";

    /**
     * Represents message about success creating user.
     */
    public static final String CREATE_USER_SUCCESS = "createUserSuccess";

    /**
     * Represents message about error creating user.
     */
    public static final String CREATE_USER_ERROR = "createUserError";

    /**
     * Represents list of last adding product on appropriate page.
     */
    public static final String LAST_PRODUCTS = "lastProducts";

    /**
     * Represents message about success adding review.
     */
    public static final String ADD_REVIEW_SUCCESS = "addReviewSuccess";

    /**
     * Represents message about error adding review.
     */
    public static final String ADD_REVIEW_ERROR = "addReviewError";

    /**
     * Represents list of reviews on appropriate page.
     */
    public static final String REVIEWS = "reviews";

    /**
     * Represents list of order and products in it on appropriate page.
     */
    public static final String ORDER_PRODUCTS = "orderProducts";

    /**
     * Represents order on appropriate page.
     */
    public static final String ORDER = "order";

    /**
     * Represents message about error creating order.
     */
    public static final String CREATE_ORDER_ERROR = "createOrderError";

    /**
     * Represents message about success deleting product from order.
     */
    public static final String DELETE_PRODUCT_FROM_ORDER_SUCCESS = "deleteProductFromOrderSuccess";

    /**
     * Represents message about error deleting product from order.
     */
    public static final String DELETE_PRODUCT_FROM_ORDER_ERROR = "deleteProductFromOrderError";

    /**
     * Represents delivery form on appropriate page.
     */
    public static final String DELIVERY_FORM = "deliveryForm";

    /**
     * Represents message about success confirming order.
     */
    public static final String CONFIRM_ORDER_SUCCESS = "confirmOrderSuccess";

    /**
     * Represents message about error confirming order.
     */
    public static final String CONFIRM_ORDER_ERROR = "confirmOrderError";

    /**
     * Represents list of orders on appropriate page.
     */
    public static final String ORDERS = "orders";

    /**
     * Represents message about success deleting review.
     */
    public static final String DELETE_REVIEW_SUCCESS = "deleteReviewSuccess";

    /**
     * Represents message about error deleting review.
     */
    public static final String DELETE_REVIEW_ERROR = "deleteReviewError";

    /**
     * Represents message about success deleting order.
     */
    public static final String DELETE_ORDER_SUCCESS = "deleteOrderSuccess";

    /**
     * Represents message about error deleting order.
     */
    public static final String DELETE_ORDER_ERROR = "deleteOrderError";

    /**
     * Represents message about error changing amount of product.
     */
    public static final String CHANGE_AMOUNT_PRODUCT_ERROR = "changeAmountError";

    private SessionAttribute() {
    }
}
