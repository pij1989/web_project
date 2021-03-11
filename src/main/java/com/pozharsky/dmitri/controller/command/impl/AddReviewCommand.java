package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.ReviewService;
import com.pozharsky.dmitri.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class AddReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddReviewCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String comment = request.getParameter(RequestParameter.COMMENT);
            String rating = request.getParameter(RequestParameter.RATING);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);
            Product product = (Product) session.getAttribute(SessionAttribute.PRODUCT);
            long userId = user.getId();
            long productId = product.getId();
            ReviewService reviewService = ReviewServiceImpl.getInstance();
            Optional<Review> optionalReview = reviewService.createReview(comment, rating, userId, productId);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                @SuppressWarnings("unchecked")
                List<Review> reviews = (List<Review>) session.getAttribute(SessionAttribute.REVIEWS);
                if (reviews != null) {
                    String username = user.getUsername();
                    review.setUsername(username);
                    reviews.add(review);
                } else {
                    reviews = reviewService.findReviewByProduct(productId);
                    session.setAttribute(SessionAttribute.REVIEWS, reviews);
                }
                session.setAttribute(SessionAttribute.ADD_REVIEW_SUCCESS, true);
            } else {
                session.setAttribute(SessionAttribute.ADD_REVIEW_ERROR, true);
            }
            Router router = new Router(PagePath.VIEW_PRODUCT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (Exception e) {
            logger.error(e);
            throw new CommandException(e);
        }

    }
}
