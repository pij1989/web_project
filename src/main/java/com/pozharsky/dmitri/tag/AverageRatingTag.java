package com.pozharsky.dmitri.tag;

import com.pozharsky.dmitri.controller.command.SessionAttribute;
import com.pozharsky.dmitri.model.entity.Review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.OptionalDouble;

public class AverageRatingTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            HttpSession session = request.getSession();
            JspWriter out = pageContext.getOut();
            @SuppressWarnings("unchecked")
            List<Review> reviews = (List<Review>) session.getAttribute(SessionAttribute.REVIEWS);
            int averageRate = defineAverageRate(reviews);
            if (averageRate != 0) {
                out.write("<div>");
                for (int i = 0; i < 5; i++) {
                    if (averageRate != 0) {
                        out.write("<span class=\"fa fa-star checked-rate\" ></span >");
                        averageRate--;
                        continue;
                    }
                    out.write("<span class=\"fa fa-star\" ></span >");
                }
                out.write("</div>");
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    private int defineAverageRate(List<Review> reviews) {
        int averageRate = 0;
        if (reviews != null) {
            OptionalDouble optionalDouble = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average();
            if (optionalDouble.isPresent()) {
                averageRate = (int) Math.round(optionalDouble.getAsDouble());
            }
        }
        return averageRate;
    }
}
