package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.util.FormatCurrencyUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

public class FormatCurrencyTag extends TagSupport {
    private static final String DEFAULT_LOCALE = "ru-BY";
    private BigDecimal value;

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            Locale locale = Locale.forLanguageTag(DEFAULT_LOCALE);
            String result = FormatCurrencyUtil.formatCurrency(value, locale);
            JspWriter out = pageContext.getOut();
            out.write(result);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
