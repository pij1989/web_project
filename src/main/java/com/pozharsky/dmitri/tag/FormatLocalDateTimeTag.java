package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.util.FormatLocalDateTimeUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class FormatLocalDateTimeTag extends TagSupport {
    private LocalDateTime date;
    private Locale locale;

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String result = FormatLocalDateTimeUtil.formatLocaleDateTime(date, locale);
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.println(result);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
