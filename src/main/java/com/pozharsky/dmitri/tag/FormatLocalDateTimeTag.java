package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.util.FormatLocalDateTimeUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class FormatLocalDateTimeTag extends SimpleTagSupport {
    private LocalDateTime date;
    private Locale locale;

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String result = FormatLocalDateTimeUtil.formatLocaleDateTime(date, locale);
        JspWriter jspWriter = getJspContext().getOut();
        jspWriter.println(result);
    }
}
