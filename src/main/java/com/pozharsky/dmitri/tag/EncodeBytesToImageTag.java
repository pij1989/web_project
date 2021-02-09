package com.pozharsky.dmitri.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Base64;

public class EncodeBytesToImageTag extends SimpleTagSupport {
    private byte[] bytes;

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String base64Image = Base64.getEncoder().encodeToString(bytes);
        JspWriter jspWriter = getJspContext().getOut();
        jspWriter.println(base64Image);
    }
}
