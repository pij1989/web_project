package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.OrderProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CartTag extends TagSupport {
    private static final String RESOURCE_NAME = "property.text";
    private static final String NAVIGATION_BUTTON_CART = "navigation.button.cart";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(SessionAttribute.LOCALE);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
        String cart = resourceBundle.getString(NAVIGATION_BUTTON_CART);
        @SuppressWarnings("unchecked")
        List<OrderProduct> orderProducts = (List<OrderProduct>) session.getAttribute(SessionAttribute.ORDER_PRODUCTS);
        try {
            JspWriter out = pageContext.getOut();
            if (orderProducts == null || orderProducts.isEmpty()) {
                out.write("<span><i class=\"fas fa-shopping-cart\"></i></span> " + cart);
            } else {
                int totalAmount = 0;
                for (OrderProduct orderProduct : orderProducts) {
                    int amount = orderProduct.getAmount();
                    totalAmount = totalAmount + amount;
                }
                out.write("<span><i class=\"fas fa-shopping-cart\"></i></span> " + cart + " " + totalAmount);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
