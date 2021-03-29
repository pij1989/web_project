package com.pozharsky.dmitri.model.service.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.dao.ReviewDao;
import com.pozharsky.dmitri.model.dao.TransactionManager;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static final ReviewServiceImpl instance = new ReviewServiceImpl();

    public static ReviewServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Review> createReview(String comment, String rating, User user, Product product) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ReviewDao reviewDao = new ReviewDao();
            transactionManager.init(reviewDao);
            int numberRating = Integer.parseInt(rating);
            LocalDateTime localDateTime = LocalDateTime.now();
            Review review = new Review(comment, numberRating, localDateTime, user, product);
            return reviewDao.create(review);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Review> findAllReviews() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ReviewDao reviewDao = new ReviewDao();
            transactionManager.init(reviewDao);
            return reviewDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public List<Review> findReviewByProduct(long productId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ReviewDao reviewDao = new ReviewDao();
            transactionManager.init(reviewDao);
            return reviewDao.findByProductId(productId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }

    @Override
    public boolean deleteReview(String reviewId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            ReviewDao reviewDao = new ReviewDao();
            transactionManager.init(reviewDao);
            long id = Long.parseLong(reviewId);
            return reviewDao.deleteById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        } finally {
            transactionManager.end();
        }
    }
}
