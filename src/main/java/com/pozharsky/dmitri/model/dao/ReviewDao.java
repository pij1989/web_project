package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class ReviewDao extends AbstractDao<Review> {
    private static final Logger logger = LogManager.getLogger(ReviewDao.class);
    private static final String CREATE_REVIEW_SQL = "INSERT INTO reviews(comment, rating, time_create, user_id, product_id) VALUES (?,?,?,?,?)";

    public boolean create(Review review) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REVIEW_SQL)) {
            preparedStatement.setString(1, review.getComment());
            preparedStatement.setInt(2, review.getRating());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(review.getCreatingTime()));
            preparedStatement.setLong(4, review.getUserId());
            preparedStatement.setLong(5, review.getProductId());
            int resultCreateReview = preparedStatement.executeUpdate();
            return resultCreateReview > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Review> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Review> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Review> update(Review entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Review entity) throws DaoException {
        return false;
    }
}
