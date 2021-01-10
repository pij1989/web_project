package com.pozharsky.dmitri.command;

public class Router {
    private String pagePath;
    private RouterType type;

    public Router(String pagePath) {
        this(pagePath, RouterType.FORWARD);
    }

    public Router(String pagePath, RouterType type) {
        this.pagePath = pagePath;
        this.type = type;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public RouterType getType() {
        return type;
    }

    public void setType(RouterType type) {
        this.type = type;
    }
}
