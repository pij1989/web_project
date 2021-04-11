package com.pozharsky.dmitri.controller.command;

/**
 * Represents constant names of request parameters.
 *
 * @author Dmitri Pozharsky
 */
public class RequestParameter {

    /**
     * Represents command from the request.
     */
    public static final String COMMAND = "command";

    /**
     * Represents changing language.
     */
    public static final String LANGUAGE = "language";

    /**
     * Represents user's first name.
     */
    public static final String FIRST_NAME = "firstName";

    /**
     * Represents user's last name.
     */
    public static final String LAST_NAME = "lastName";

    /**
     * Represents user's username.
     */
    public static final String USERNAME = "username";

    /**
     * Represents user's email.
     */
    public static final String EMAIL = "email";

    /**
     * Represents user's password.
     */
    public static final String PASSWORD = "password";

    /**
     * Represents user's old password for changing password.
     */
    public static final String OLD_PASSWORD = "oldPassword";

    /**
     * Represents user's new password for changing password.
     */
    public static final String NEW_PASSWORD = "newPassword";

    /**
     * Represents user's confirm password.
     */
    public static final String CONFIRM_PASSWORD = "confirmPassword";

    /**
     * Represents user's role.
     */
    public static final String ROLE = "role";

    /**
     * Represents user's or order's status.
     */
    public static final String STATUS = "status";

    /**
     * Represents token value.
     */
    public static final String TOKEN = "token";

    /**
     * Represents user's ID.
     */
    public static final String USER_ID = "userId";

    /**
     * Represents category's ID.
     */
    public static final String CATEGORY_ID = "categoryId";

    /**
     * Represents product's name.
     */
    public static final String PRODUCT_NAME = "productName";

    /**
     * Represents product's ID.
     */
    public static final String PRODUCT_ID = "productId";

    /**
     * Represents category's name.
     */
    public static final String CATEGORY_NAME = "categoryName";

    /**
     * Represents category's name.
     */
    public static final String CATEGORY = "category";

    /**
     * Represents product's price.
     */
    public static final String PRICE = "price";

    /**
     * Represents product's active or not active state.
     */
    public static final String IS_ACTIVE_PRODUCT = "isActiveProduct";

    /**
     * Represents product's description.
     */
    public static final String DESCRIPTION = "description";

    /**
     * Represents product's image.
     */
    public static final String IMAGE = "image";

    /**
     * Represents product's creating time.
     */
    public static final String TIME_CREATE = "creatingTime";

    /**
     * Represents amount product on page.
     */
    public static final String PER_PAGE = "perPage";

    /**
     * Represents number of page.
     */
    public static final String PAGE = "page";

    /**
     * Represents searching product name.
     */
    public static final String SEARCH_PRODUCT = "searchProduct";

    /**
     * Represents product's amount.
     */
    public static final String AMOUNT = "amount";

    /**
     * Represents review's comment.
     */
    public static final String COMMENT = "comment";

    /**
     * Represents review's rating.
     */
    public static final String RATING = "rating";

    /**
     * Represents amount product one category in order.
     */
    public static final String AMOUNT_PRODUCT = "amountProduct";

    /**
     * Represents ID of order_products table.
     */
    public static final String ORDER_PRODUCT_ID = "orderProductId";

    /**
     * Represents sort type of product.
     */
    public static final String SORT = "sort";

    /**
     * Represents price from in product filter.
     */
    public static final String PRICE_FROM = "priceFrom";

    /**
     * Represents price to in product filter.
     */
    public static final String PRICE_TO = "priceTo";

    /**
     * Represents product in stock or not available in product filter.
     */
    public static final String IN_STOCK = "inStock";

    /**
     * Represents delivery's city.
     */
    public static final String CITY = "city";

    /**
     * Represents delivery's street.
     */
    public static final String STREET = "street";

    /**
     * Represents delivery's home.
     */
    public static final String HOME_NUMBER  ="homeNumber";

    /**
     * Represents delivery's phone.
     */
    public static final String PHONE = "phone";

    /**
     * Represents order's ID.
     */
    public static final String ORDER_ID = "orderId";

    /**
     * Represents review's ID.
     */
    public static final String REVIEW_ID = "reviewId";

    /**
     * Represents order's status.
     */
    public static final String ORDER_STATUS = "orderStatus";

    private RequestParameter() {
    }
}
