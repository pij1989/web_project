package com.pozharsky.dmitri.model.dao.impl;

import com.pozharsky.dmitri.exception.DaoException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.dao.UserDao;
import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    //SQL-expression
    private static final String CREATE_USER_SQL = "INSERT INTO users (first_name,last_name,username,email,password,role_id,status_id) VALUES (?,?,?,?,?,?,?);";
    private static final String FIND_ROLE_ID_BY_NAME_SQL = "SELECT id FROM roles WHERE role_name = ?;";
    private static final String FIND_STATUS_ID_BY_NAME_SQL = "SELECT id FROM status WHERE status_name = ?;";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT u.id,u.first_name,u.last_name,u.username,u.email,r.role_name,s.status_name FROM users AS u INNER JOIN roles AS r ON u.role_id = r.id INNER JOIN status AS s ON u.status_id = s.id WHERE u.email = ?";
    private static final String FIND_PASSWORD_BY_EMAIL_SQL = "SELECT password FROM users WHERE email = ?";
    //name of column
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ROLE_NAME = "role_name";
    private static final String STATUS_NAME = "status_name";

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean create(User user) throws DaoException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        boolean isCreateUser = false;
        try (
                PreparedStatement userPreparedStatement = connection.prepareStatement(CREATE_USER_SQL);
                PreparedStatement rolePreparedStatement = connection.prepareStatement(FIND_ROLE_ID_BY_NAME_SQL);
                PreparedStatement statusPreparedStatement = connection.prepareStatement(FIND_STATUS_ID_BY_NAME_SQL)
        ) {
            connection.setAutoCommit(false);
            rolePreparedStatement.setString(1, user.getRoleType().toString());
            statusPreparedStatement.setString(1, user.getStatusType().toString());
            ResultSet roleKeys = rolePreparedStatement.executeQuery();
            ResultSet statusKeys = statusPreparedStatement.executeQuery();
            userPreparedStatement.setString(1, user.getFirstName());
            userPreparedStatement.setString(2, user.getLastName());
            userPreparedStatement.setString(3, user.getUsername());
            userPreparedStatement.setString(4, user.getEmail());
            userPreparedStatement.setString(5, user.getPassword());
            if (roleKeys.next()) {
                int roleId = roleKeys.getInt(ID);
                userPreparedStatement.setInt(6, roleId);
            }
            if (statusKeys.next()) {
                int statusId = statusKeys.getInt(ID);
                userPreparedStatement.setInt(7, statusId);
            }
            int resultCreateUser = userPreparedStatement.executeUpdate();
            if (resultCreateUser == 1) {
                isCreateUser = true;
            }
            connection.commit();
            return isCreateUser;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Impossible rollback committing");
            }
            logger.error("Impossible create user in database");
            throw new DaoException("Database problem when create user: " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("Impossible return connection to connection poll");
            }
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setUsername(resultSet.getString(USERNAME));
                user.setEmail(resultSet.getString(EMAIL));
                user.setRoleType(RoleType.valueOf(resultSet.getString(ROLE_NAME)));
                user.setStatusType(StatusType.valueOf(resultSet.getString(STATUS_NAME)));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public String findPasswordByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL_SQL)) {
            String password = "";
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(PASSWORD);
            }
            return password;
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
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public User update(User entity) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }
}
