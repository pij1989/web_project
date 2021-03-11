package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.ReviewService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import com.pozharsky.dmitri.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ViewProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ViewProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String stringProductId = request.getParameter(RequestParameter.PRODUCT_ID);
        long productId;
        try {
            productId = Long.parseLong(stringProductId);
        } catch (NumberFormatException e) {
            logger.error("Incorrect product id", e);
            return new Router(PagePath.ERROR_404, Router.Type.REDIRECT);
        }
        try {
            ProductService productService = ProductServiceImpl.getInstance();
            Optional<Product> optionalProduct = productService.findProductById(productId);
            HttpSession session = request.getSession();
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                session.setAttribute(SessionAttribute.PRODUCT, product);
                ReviewService reviewService = ReviewServiceImpl.getInstance();
                List<Review> reviews = reviewService.findReviewByProduct(productId);
                session.setAttribute(SessionAttribute.REVIEWS, reviews);
            }
            Router router = new Router(PagePath.VIEW_PRODUCT);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, router);
            return router;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
