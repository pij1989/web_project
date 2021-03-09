package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;

public interface ReviewService {
    boolean createReview(String comment, String rating, long userId,long productId) throws ServiceException;
}
