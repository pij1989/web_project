package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.util.PaginationUtil;
import com.pozharsky.dmitri.validator.PageValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PaginationTag extends TagSupport {
    private static final int DEFAULT_PER_PAGE = 5;
    private static final int DEFAULT_PAGE = 1;
    private static final int SELECT_OPTION_ONE = 5;
    private static final int SELECT_OPTION_TWO = 10;
    private static final int SELECT_OPTION_THREE = 20;
    private static final String RESOURCE_NAME = "property.text";
    private static final String PAGINATION_PAGE = "pagination.page";
    private static final String PAGINATION_BUTTON_NEXT = "pagination.button.next";
    private static final String PAGINATION_BUTTON_PREVIOUS = "pagination.button.previous";

    private int amountItem;

    public void setAmountItem(int amountItem) {
        this.amountItem = amountItem;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();
        String page = request.getParameter(RequestParameter.PAGE);
        String perPage = request.getParameter(RequestParameter.PER_PAGE);
        int numberPage;
        int numberPerPage;
        if (PageValidator.isValidPage(page, perPage)) {
            numberPerPage = Integer.parseInt(perPage);
            numberPage = Integer.parseInt(page);
        } else {
            numberPerPage = DEFAULT_PER_PAGE;
            numberPage = DEFAULT_PAGE;
        }
        int amountPage = PaginationUtil.defineAmountPage(amountItem, numberPerPage);
        Locale locale = (Locale) session.getAttribute(SessionAttribute.LOCALE);
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
        String rowPerPage = bundle.getString(PAGINATION_PAGE);
        String buttonNext = bundle.getString(PAGINATION_BUTTON_NEXT);
        String buttonPrevious = bundle.getString(PAGINATION_BUTTON_PREVIOUS);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div class=\"form-group\">");
            out.write("<label for=\"rowsPerPage\">" + rowPerPage + " </label>");
            out.write("<select name=\"perPage\" class=\"form-control\" id=\"rowsPerPage\">");
            out.write("<option");
            if (numberPerPage == SELECT_OPTION_ONE) {
                out.write(" selected ");
            }
            out.write(">" + SELECT_OPTION_ONE + "</option>");
            out.write("<option");
            if (numberPerPage == SELECT_OPTION_TWO) {
                out.write(" selected ");
            }
            out.write(">" + SELECT_OPTION_TWO + "</option>");
            out.write("<option");
            if (numberPerPage == SELECT_OPTION_THREE) {
                out.write(" selected ");
            }
            out.write(">" + SELECT_OPTION_THREE + "</option>");
            out.write("</select>");
            out.write("</div>");
            out.write("<div class=\"form-group\"style=\"padding-top: 16px; padding-left: 16px\">");
            out.write("<nav>");
            out.write("<ul class=\"pagination\">");
            out.write("<input type=\"hidden\"name=\"page\"value=\"\"id=\"page\">");
            out.write(" <li id=\"previous\" class=\"page-item ");
            if (numberPage == 1) {
                out.write("disabled");
            }
            out.write("\"><a class=\"page-link\">" + buttonPrevious + "</a></li>");
            for (int i = 1; i <= amountPage; i++) {
                if (i == numberPage) {
                    out.write("<li class=\"page-item active\"><input id=\"active\" class=\"page-link\"name=\"button\"type=\"button\"value=\"" + i + "\"></li>");
                } else {
                    out.write("<li class=\"page-item\"><input class=\"page-link\"name=\"button\"type=\"button\"value=\"" + i + "\"></li>");
                }
            }
            out.write(" <li id=\"next\" class=\"page-item ");
            if (numberPage == amountPage) {
                out.write("disabled");
            }
            out.write("\"><a class=\"page-link\">" + buttonNext + "</a></li>");
            out.write("</ul>");
            out.write("</nav>");
            out.write("</div>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}






