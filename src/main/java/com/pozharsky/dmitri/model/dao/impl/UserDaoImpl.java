package com.pozharsky.dmitri.model.dao.impl;

import com.pozharsky.dmitri.model.connector.ConnectionCreator;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String CREATE_USER_SQL = "INSERT INTO user (first_name,last_name,email,password,role_type) VALUES (?,?,?,?,?);";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT * FROM user WHERE email = ?";
    public static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    @Override
    public boolean create(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRoleType().toString());
            int result = preparedStatement.executeUpdate();
            logger.debug(result);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                logger.debug(resultSet.getInt(1));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public User findById(long id) {
        return new User();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleType(RoleType.valueOf(resultSet.getString("role_type")));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
