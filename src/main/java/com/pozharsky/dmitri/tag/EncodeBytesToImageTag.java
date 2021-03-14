package com.pozharsky.dmitri.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Base64;

public class EncodeBytesToImageTag extends TagSupport {
    private byte[] bytes;

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            JspWriter out = pageContext.getOut();
            out.write(base64Image);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
