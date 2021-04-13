package com.pozharsky.dmitri.model.dao;

/**
 * Class contains constant names of tables' columns.
 *
 * @author Dmitri Pozharsky
 */
class ColumnName {

    /**
     * Represents ID column at table.
     */
    static final String ID = "id";

    /**
     * Represents user's ID column at table.
     */
    static final String USER_ID = "user_id";

    /**
     * Represents user's first name column at users table.
     */
    static final String FIRST_NAME = "first_name";

    /**
     * Represents user's last name column at users table.
     */
    static final String LAST_NAME = "last_name";

    /**
     * Represents user's username column at users table.
     */
    static final String USERNAME = "username";

    /**
     * Represents user's email column at users table.
     */
    static final String EMAIL = "email";

    /**
     * Represents user's password column at users table.
     */
    static final String PASSWORD = "password";

    /**
     * Represents user's role column at roles table.
     */
    static final String ROLE_NAME = "role_name";

    /**
     * Represents user's status column at status table.
     */
    static final String STATUS_NAME = "status_name";

    /**
     * Represents token's value column at tokens table.
     */
    static final String TOKEN_VALUE = "token_value";

    /**
     * Represents time create column at table.
     */
    static final String TIME_CREATE = "time_create";

    /**
     * Represents token's time expire column at tokens table.
     */
    static final String TIME_EXPIRE = "time_expire";

    /**
     * Represents category's ID column at table.
     */
    static final String CATEGORY_ID = "category_id";

    /**
     * Represents category's name column at categories table.
     */
    static final String CATEGORY_NAME = "category_name";

    /**
     * Represents product's name column at products table.
     */
    static final String PRODUCT_NAME = "product_name";

    /**
     * Represents product's price column at products table.
     */
    static final String PRICE = "price";

    /**
     * Represents product's description column at products table.
     */
    static final String DESCRIPTION = "description";

    /**
     * Represents product's status column at products table.
     */
    static final String STATUS = "status";

    /**
     * Represents product's image column at products table.
     */
    static final String IMAGE = "image";

    /**
     * Represents product's amount column at products table.
     */
    static final String AMOUNT = "amount";

    /**
     * Represents review's comment column at reviews table.
     */
    static final String COMMENT = "comment";

    /**
     * Represents review's rating column at reviews table.
     */
    static final String RATING = "rating";

    /**
     * Represents product's ID column at table.
     */
    static final String PRODUCT_ID = "product_id";

    /**
     * Represents order's cost column at orders table.
     */
    static final String COST = "cost";

    /**
     * Represents order's status name column at orders_status table.
     */
    static final String ORDER_STATUS_NAME = "order_status_name";

    /**
     * Represents amount product column at order_products table.
     */
    static final String AMOUNT_PRODUCT = "amount_product";

    /**
     * Represents order's ID column at table.
     */
    static final String ORDER_ID = "order_id";

    /**
     * Represents total price column at order_products table.
     */
    static final String TOTAL_PRICE = "total_price";

    /**
     * Represents delivery's city column at deliveries table.
     */
    static final String CITY = "city";

    /**
     * Represents delivery's street column at deliveries table.
     */
    static final String STREET = "street";

    /**
     * Represents delivery's home number column at deliveries table.
     */
    static final String HOME_NUMBER = "home_number";

    /**
     * Represents delivery's phone number column at deliveries table.
     */
    static final String PHONE_NUMBER = "phone_number";

    private ColumnName() {
    }
}
