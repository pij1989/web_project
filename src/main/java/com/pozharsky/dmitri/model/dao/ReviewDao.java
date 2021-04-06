package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Product;
import com.pozharsky.dmitri.model.entity.Review;
import com.pozharsky.dmitri.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDao extends AbstractDao<Review> {
    private static final Logger logger = LogManager.getLogger(ReviewDao.class);
    private static final String CREATE_REVIEW_SQL = "INSERT INTO reviews(comment, rating, time_create, user_id, product_id) VALUES (?,?,?,?,?);";
    private static final String FIND_REVIEW_BY_PRODUCT_ID_SQL = "SELECT r.id, r.comment, r.rating, r.time_create, r.user_id, r.product_id, u.username, u.email FROM reviews AS r JOIN users AS u on r.user_id = u.id WHERE r.product_id = ?;";
    private static final String FIND_ALL_REVIEWS_SQL = "SELECT r.id, r.comment, r.rating, r.time_create, r.user_id, r.product_id, u.username, u.email, p.product_name FROM reviews AS r JOIN users AS u ON r.user_id = u.id JOIN products AS p ON r.product_id = p.id;";
    private static final String DELETE_REVIEW_BY_ID_SQL = "DELETE FROM reviews WHERE id = ?;";


    public Optional<Review> create(Review review) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REVIEW_SQL, Statement.RETURN_GENERATED_KEYS)) {
            User user = review.getUser();
            Product product = review.getProduct();
            preparedStatement.setString(1, review.getComment());
            preparedStatement.setInt(2, review.getRating());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(review.getCreatingTime()));
            preparedStatement.setLong(4, user.getId());
            preparedStatement.setLong(5, product.getId());
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
    public Optional<Review> findById(long id) {
        throw new UnsupportedOperationException("Unsupported operation 'findById' for ReviewDao");
    }

    @Override
    public List<Review> findAll() throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_REVIEWS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (resultSet.next()) {
                Review review = createReviewFromResultSet(resultSet);
                Product product = review.getProduct();
                product.setName(resultSet.getString(ColumnName.PRODUCT_NAME));
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Review> update(Review entity) {
        throw new UnsupportedOperationException("Unsupported operation 'update' for ReviewDao");
    }

    @Override
    public boolean deleteById(long reviewId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEW_BY_ID_SQL)) {
            preparedStatement.setLong(1, reviewId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private Review createReviewFromResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        User user = new User();
        Product product = new Product();
        review.setId(resultSet.getLong(ColumnName.ID));
        review.setComment(resultSet.getString(ColumnName.COMMENT));
        review.setRating(resultSet.getInt(ColumnName.RATING));
        review.setCreatingTime(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        product.setId(resultSet.getLong(ColumnName.PRODUCT_ID));
        user.setId(resultSet.getLong(ColumnName.USER_ID));
        user.setUsername(resultSet.getString(ColumnName.USERNAME));
        user.setEmail(resultSet.getString(ColumnName.EMAIL));
        review.setProduct(product);
        review.setUser(user);
        return review;
    }
}
