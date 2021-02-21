package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.util.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private static final int DEFAULT_PER_PAGE = 5;
    private static final int DEFAULT_PAGE = 1;
    private static final int SELECT_OPTION_ONE = 5;
    private static final int SELECT_OPTION_TWO = 10;
    private static final int SELECT_OPTION_THREE = 20;

    private int amountItem;

    public void setAmountItem(int amountItem) {
        this.amountItem = amountItem;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String page = request.getParameter(RequestParameter.PAGE);
        String perPage = request.getParameter(RequestParameter.PER_PAGE);
        int numberPage = page != null ? Integer.parseInt(page) : DEFAULT_PAGE;
        int numberPerPage = perPage != null ? Integer.parseInt(perPage) : DEFAULT_PER_PAGE;
        int amountPage = PaginationUtil.defineAmountPage(amountItem, numberPerPage);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div class=\"form-group\">");
            out.write("<label for=\"rowsPerPage\">Rows per page: </label>");
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
            for (int i = 1; i <= amountPage; i++) {
                if (i == numberPage) {
                    out.write("<li class=\"page-item active\"><input class=\"page-link\"name=\"button\"type=\"button\"value=\"" + i + "\"></li>");
                } else {
                    out.write("<li class=\"page-item\"><input class=\"page-link\"name=\"button\"type=\"button\"value=\"" + i + "\"></li>");
                }
            }
            out.write("</ul>");
            out.write("</nav>");
            out.write("</div>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}






