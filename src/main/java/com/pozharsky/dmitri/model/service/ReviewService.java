package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Optional<Review> createReview(String comment, String rating, User user, Product product) throws ServiceException;

    List<Review> findAllReviews() throws ServiceException;

    List<Review> findReviewByProduct(long productId) throws ServiceException;

    boolean deleteReview(String reviewId) throws ServiceException;
}
