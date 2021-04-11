package com.pozharsky.dmitri.controller.command;

/**
 * Class represents router
 *
 * @author Dmitri Pozharsky
 */
public class Router {

    /**
     * Enumeration of the router's type.
     *
     * @author Dmitri Pozharsky
     */
    public enum Type {
        FORWARD,
        REDIRECT
    }

    private String pagePath;
    private Type type;

    /**
     * Constructs a Router object with given page path.
     *
     * @param pagePath String object of page path.
     */
    public Router(String pagePath) {
        this(pagePath, Type.FORWARD);
    }

    /**
     * Constructs a Router object with given page path and router's type.
     *
     * @param pagePath String object of page path.
     * @param type Router.Type object of router's type.
     */
    public Router(String pagePath, Type type) {
        this.pagePath = pagePath;
        this.type = type;
    }

    /**
     * Getter method of page path.
     *
     * @return String object of page path.
     */
    public String getPagePath() {
        return pagePath;
    }

    /**
     *  Setter method of page path.
     *
     * @param pagePath String object of page path.
     */
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    /**
     * Getter method of router's type.
     *
     * @return Router.Type object of router's type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Setter method of router's type.
     *
     * @param type Router.Type object of router's type.
     */
    public void setType(Type type) {
        this.type = type;
    }
}
