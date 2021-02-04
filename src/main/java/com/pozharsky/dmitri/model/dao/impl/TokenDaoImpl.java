package com.pozharsky.dmitri.model.dao.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.dao.ColumnName;
import com.pozharsky.dmitri.model.dao.TokenDao;
import com.pozharsky.dmitri.model.entity.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TokenDaoImpl implements TokenDao {
    private static final Logger logger = LogManager.getLogger(TokenDaoImpl.class);
    private static final String FIND_TOKEN_BY_VALUE_SQL = "SELECT id, token_value, time_create, time_expire,user_id FROM tokens WHERE token_value = ?";
    private static final String FIND_TOKEN_BY_USER_EMAIL_SQL = "SELECT t.id,t.token_value,t.time_create,t.time_expire,t.user_id FROM tokens AS t INNER JOIN users AS u ON u.id = t.user_id WHERE u.email = ?";

    private static final TokenDaoImpl instance = new TokenDaoImpl();

    public static TokenDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Token> findTokenByValue(String tokenValue) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_VALUE_SQL)) {
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

    @Override
    public Optional<Token> findTokenByUserEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN_BY_USER_EMAIL_SQL)) {
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
    public Optional<Token> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Token> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Token> update(Token entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Token entity) throws DaoException {
        return false;
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
