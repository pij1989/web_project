package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDao extends AbstractDao<Review> {
    private static final Logger logger = LogManager.getLogger(ReviewDao.class);
    private static final String CREATE_REVIEW_SQL = "INSERT INTO reviews(comment, rating, time_create, user_id, product_id) VALUES (?,?,?,?,?)";
    private static final String FIND_REVIEW_BY_PRODUCT_ID_SQL = "SELECT r.id, r.comment, r.rating, r.time_create, r.user_id, r.product_id, u.username FROM reviews AS r JOIN users AS u on r.user_id = u.id WHERE r.product_id = ?";


    public Optional<Review> create(Review review) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REVIEW_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, review.getComment());
            preparedStatement.setInt(2, review.getRating());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(review.getCreatingTime()));
            preparedStatement.setLong(4, review.getUserId());
            preparedStatement.setLong(5, review.getProductId());
            int resultCreateReview = preparedStatement.executeUpdate();
            if (resultCreateReview > 0) {
                ResultSet reviewKeys = preparedStatement.getGeneratedKeys();
                if (reviewKeys.next()) {
                    review.setId(reviewKeys.getLong(1));
                    return Optional.of(review);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public List<Review> findByProductId(long productId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_REVIEW_BY_PRODUCT_ID_SQL)) {
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (resultSet.next()) {
                Review review = createReviewFromResultSet(resultSet);
                reviews.add(review);
            }
            return reviews;
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

    private Review createReviewFromResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong(ColumnName.ID));
        review.setComment(resultSet.getString(ColumnName.COMMENT));
        review.setRating(resultSet.getInt(ColumnName.RATING));
        review.setCreatingTime(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        review.setProductId(resultSet.getLong(ColumnName.PRODUCT_ID));
        review.setUserId(resultSet.getLong(ColumnName.USER_ID));
        review.setUsername(resultSet.getString(ColumnName.USERNAME));
        return review;
    }
}
