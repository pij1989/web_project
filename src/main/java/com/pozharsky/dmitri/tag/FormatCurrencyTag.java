package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.util.FormatCurrencyUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

public class FormatCurrencyTag extends SimpleTagSupport {
    private BigDecimal value;
    private Locale locale;
    private static final BigDecimal COURSE = BigDecimal.valueOf(2.6);

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String result = FormatCurrencyUtil.formatCurrency(value, locale);
        JspWriter jspWriter = getJspContext().getOut();
        jspWriter.println(result);
    }
}
