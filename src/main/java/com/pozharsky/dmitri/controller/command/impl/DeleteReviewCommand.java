package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.service.ReviewService;
import com.pozharsky.dmitri.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteReviewCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String reviewId = request.getParameter(RequestParameter.REVIEW_ID);
            HttpSession session = request.getSession();
            ReviewService reviewService = ReviewServiceImpl.getInstance();
            if (reviewService.deleteReview(reviewId)) {
                session.setAttribute(SessionAttribute.DELETE_REVIEW_SUCCESS, true);
                @SuppressWarnings("unchecked")
                List<Review> reviews = (List<Review>) session.getAttribute(SessionAttribute.REVIEWS);
                if (reviews != null) {
                    reviews = reviews.stream()
                            .filter(e -> e.getId() != Long.parseLong(reviewId))
                            .collect(Collectors.toList());
                } else {
                    reviews = reviewService.findAllReviews();
                }
                session.setAttribute(SessionAttribute.REVIEWS, reviews);
            } else {
                session.setAttribute(SessionAttribute.DELETE_REVIEW_ERROR, true);
            }
            Router router = new Router(PagePath.REVIEWS, Router.Type.REDIRECT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
