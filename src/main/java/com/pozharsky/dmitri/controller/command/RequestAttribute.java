package com.pozharsky.dmitri.controller.command;

/**
 * Class represents constant names of request attributes.
 *
 * @author Dmitri Pozharsky
 */
public class RequestAttribute {

    /**
     * Represents message about category which not exist.
     */
    public static final String CATEGORY_NOT_EXIST = "categoryNotExist";

    /**
     * Represents message about product which not exist.
     */
    public static final String PRODUCT_NOT_EXIST = "productNotExist";

    /**
     * Represents message about empty order.
     */
    public static final String ORDER_IS_EMPTY = "orderIsEmpty";

    /**
     * The attribute is used in sort form of product.
     */
    public static final String SORT_TYPE = "sortType";

    /**
     * The attribute is used on page products with filters.
     */
    public static final String WITH_FILTER = "withFilter";

    /**
     * The attribute is used in filter form of product.
     */
    public static final String FILTER_PRODUCT_FORM = "filterProductForm";

    /**
     * Represents list of products on appropriate page.
     */
    public static final String PRODUCTS = "products";

    /**
     * Represents list of searching products on appropriate page.
     */
    public static final String SEARCH_PRODUCT = "searchProduct";

    /**
     * Represents list of orders and products in it on appropriate page.
     */
    public static final String ORDER_PRODUCTS = "orderProducts";

    /**
     * Represents order on appropriate page.
     */
    public static final String ORDER = "order";

    /**
     * Represents order on appropriate page.
     */
    public static final String VIEW_ORDER = "viewOrder";

    /**
     * Represents delivery on appropriate page.
     */
    public static final String DELIVERY = "delivery";

    /**
     * Represents user on appropriate page.
     */
    public static final String USER = "user";

    /**
     * Represents user on appropriate page.
     */
    public static final String VIEW_USER ="viewUser";

    private RequestAttribute() {
    }
}
