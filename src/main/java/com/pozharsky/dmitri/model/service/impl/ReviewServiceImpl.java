package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.ReviewDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static final ReviewServiceImpl instance = new ReviewServiceImpl();

    public static ReviewServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createReview(String comment, String rating, long userId, long productId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ReviewDao reviewDao = new ReviewDao();
            transactionManager.init(reviewDao);
            int numberRating = Integer.parseInt(rating);
            LocalDateTime localDateTime = LocalDateTime.now();
            Review review = new Review(comment, numberRating, localDateTime, userId, productId);
            return reviewDao.create(review);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }
}
