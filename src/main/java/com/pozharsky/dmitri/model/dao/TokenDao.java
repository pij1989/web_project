package com.pozharsky.dmitri.model.dao;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.entity.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class TokenDao extends AbstractDao<Token> {
    private static final Logger logger = LogManager.getLogger(TokenDao.class);
    private static final String CREATE_TOKEN_SQL = "INSERT INTO tokens (token_value, time_create, time_expire, user_id) VALUES (?,?,?,?)";
    private static final String FIND_TOKEN_BY_VALUE_SQL = "SELECT id, token_value, time_create, time_expire,user_id FROM tokens WHERE token_value = ?";
    private static final String FIND_TOKEN_BY_USER_EMAIL_SQL = "SELECT t.id,t.token_value,t.time_create,t.time_expire,t.user_id FROM tokens AS t INNER JOIN users AS u ON u.id = t.user_id WHERE u.email = ? ORDER BY time_create DESC";

    public boolean create(Token token, long userId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TOKEN_SQL);) {
            preparedStatement.setString(1, token.getTokenValue());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(token.getTimeCreate()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(token.getTimeExpire()));
            preparedStatement.setLong(4, userId);
            int resultCreateToken = preparedStatement.executeUpdate();
            return resultCreateToken > 0;
        } catch (SQLException e) {
            logger.error("Impossible create token in database");
            throw new DaoException(e);
        }
    }

    public Optional<Token> findTokenByValue(String tokenValue) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_VALUE_SQL)) {
            preparedStatement.setString(1, tokenValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Token token = createTokenFromResultSet(resultSet);
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Impossible find token by token value: " + e);
            throw new DaoException(e);
        }
    }

    public Optional<Token> findTokenByUserEmail(String email) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_USER_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Token token = createTokenFromResultSet(resultSet);
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Token> findById(long id) {
        throw new UnsupportedOperationException("Unsupported operation 'findById' for TokenDao");
    }

    @Override
    public List<Token> findAll() {
        throw new UnsupportedOperationException("Unsupported operation 'findAll' for TokenDao");
    }

    @Override
    public Optional<Token> update(Token entity) {
        throw new UnsupportedOperationException("Unsupported operation 'update' for TokenDao");
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException("Unsupported operation 'deleteById' for TokenDao");
    }

    private Token createTokenFromResultSet(ResultSet resultSet) throws SQLException {
        Token token = new Token();
        token.setId(resultSet.getLong(ColumnName.ID));
        token.setTokenValue(resultSet.getString(ColumnName.TOKEN_VALUE));
        token.setTimeCreate(resultSet.getTimestamp(ColumnName.TIME_CREATE).toLocalDateTime());
        token.setTimeExpire(resultSet.getTimestamp(ColumnName.TIME_EXPIRE).toLocalDateTime());
        token.setUserId(resultSet.getLong(ColumnName.USER_ID));
        return token;
    }
}
