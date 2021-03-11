package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Optional<Review> createReview(String comment, String rating, long userId, long productId) throws ServiceException;

    List<Review> findReviewByProduct(long productId) throws ServiceException;
}
